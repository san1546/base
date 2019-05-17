package com.example.eurakeclient.task;

import com.example.eurakeclient.model.Mail;
import org.apache.commons.mail.util.MimeMessageParser;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.security.Security;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.example.eurakeclient.common.EmojiFilter.filterEmoji;

public class EmailUtil {

    public static void CheckEmail()throws Exception {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";//ssl加密,jdk1.8无法使用

        String port = "993";
        String imapServer = "imap.jnu.edu.cn";
        String protocol = "imap";
//        String username = "auth@jnu.edu.cn";
//        String password = "Jnu601!!"; // 暨南邮箱的授权码
        String username = "ohyh23@jnu.edu.cn";
        String password = "Hyuheng123"; // 暨南邮箱的授权码

        //有些参数可能不需要
        Properties props = new Properties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.fallback", "false");
        props.setProperty("mail.transport.protocol", protocol); // 使用的协议
        props.setProperty("mail.imap.port", port);
        props.setProperty("mail.imap.socketFactory.port", port);

        // 获取连接
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);

        // 获取Store对象
        Store store = session.getStore(protocol);
        store.connect(imapServer, username, password); // 登陆认证


        // 通过imap协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
        Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
        folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限

        int n = folder.getUnreadMessageCount();// 得到未读数量
        System.out.println("unread:" + n);

//        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // false代表未读，true代表已读
//        Message messages[] = folder.search(ft);
        Message messages[] = folder.getMessages();
        for (Message message : messages) {
            String subject = message.getSubject();// 获得邮件主题
            Address from = (Address) message.getFrom()[0];// 获得发送者地址
            System.out.println("邮件的主题为: " + subject);
            System.out.println("发件人地址为: "+decodeText(from.toString()));
            System.out.println("日期:" + message.getSentDate());
//            System.out.println("邮件类型："+message.getContent());
            if(message.getContent() instanceof String)
            {
                String body =(String)message.getContent();
//                System.out.println("\n邮件内容: "+body);
                Mail mail = new Mail();
                mail.setFromemail(from.toString());
                mail.setReplyto(message.getReplyTo()[0].toString());
                mail.setContent(filterEmoji(body.toString()));
                mail.setSubject(subject);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                mail.setReceiptTime(format.format(message.getSentDate()));
                insertMail(mail,connectSql());
            }
            else if(message.getContent() instanceof Multipart)
            {
                System.out.println("多媒体\n");
                parseMultipart((Multipart) message.getContent(),message);
            }

            message.setFlag(Flags.Flag.SEEN, true);     //imap读取后邮件状态会变为已读,设为未读
        }

        folder.close(false);// 关闭邮件夹对象
        store.close(); // 关闭连接对象
    }


    /**
     * 连接数据库
     * ZHDX zhdx
     */
    public static Connection connectSql() {
        Connection conn = null;
        StringBuffer sqlstr = new StringBuffer();
        ResultSet rs=null;
        /*正式库库*/
        String url = "jdbc:mysql://192.168.19.249:3306/mynet?autoReconnect=true&amp;";
        String user= "root";
        String password="san1546";


        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url,user,password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库数据库
     * ZHDX zhdx
     */
    public static void closeSql(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加邮件内容
     *
     * @param mail
     */
    public static void insertMail(Mail mail, Connection conn) {

        String sql = "insert into email_content (fromEmail,subject,replyTo,content,attachment,receipt_time) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mail.getFromemail());
            pstmt.setString(2, mail.getSubject());
            pstmt.setString(3, mail.getReplyto());
            pstmt.setString(4, mail.getContent());
            pstmt.setString(5, mail.getAttachment());
            pstmt.setString(6, mail.getReceiptTime());
            pstmt.executeUpdate();
            System.out.println("插入成功！");
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected static String decodeText(String text) throws UnsupportedEncodingException {
        if (text == null)
            return null;
        if (text.startsWith("=?GB") || text.startsWith("=?gb"))
            text = MimeUtility.decodeText(text);
        else
            text = new String(text.getBytes("ISO8859_1"));
        return text;
    }


    /**
     * 对复杂邮件的解析
     * @param multipart
     * @throws MessagingException
     * @throws IOException
     */
    public static void parseMultipart(Multipart multipart, Message message) throws MessagingException, IOException {
        int count = multipart.getCount();

        boolean result = false;
        for (int idx=0;idx<count;idx++) {
            BodyPart bodyPart = multipart.getBodyPart(idx);
//            System.out.println(bodyPart.getContentType());
            if (bodyPart.isMimeType("text/plain")) {
//                System.out.println("plain................."+bodyPart.getContent());
            } else if(bodyPart.isMimeType("text/html")) {
//                System.out.println("html..................."+bodyPart.getContent());
                if(result==false) {
                    try {
                        MimeMessageParser parser = new MimeMessageParser((MimeMessage) message).parse();
                        String from = parser.getFrom(); // 获取发件人地址
                        List<Address> cc = parser.getCc();// 获取抄送人地址
                        List<Address> to = parser.getTo(); // 获取收件人地址
                        String replyTo = parser.getReplyTo();// 获取回复邮件时的收件人
                        String subject = parser.getSubject(); // 获取邮件主题
                        String htmlContent = parser.getHtmlContent(); // 获取Html内容
                        String plainContent = parser.parse().getPlainContent(); // 获取纯文本邮件内容（注：有些邮件不支持html）
                        java.util.Date date = message.getReceivedDate();

                        Document doc = Jsoup.parse(htmlContent);
                        Elements content = doc.getElementsByTag("body");
                        String[] str = content.text().split("。");

                        String ss = "";
                        for(int i=0;i<str.length;i++){
                            str[i]=str[i]+"\n";
//                            System.out.println("str["+i+"]:"+str[i]);
                        }
                        for(int i=0;i<str.length;i++){
                            ss+=str[i];
                        }
//                        System.out.println("邮件内容:"+ss);
//                        for(Element link : content){
//                            System.out.println("Element:"+link);
//                            String linkText = link.text();
//                            System.out.println("邮件内容:"+linkText);
//                        }

                        Mail mail = new Mail();
                        String to1 = "";
                        for(Address address:to){
                            to1+=address+";";
                        }
                        mail.setFromemail(from);
                        mail.setReplyto(to1);
                        mail.setContent(ss);
                        mail.setSubject(subject);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        mail.setReceiptTime(format.format(date));
                        insertMail(mail,connectSql());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                result=true;
            } else if(bodyPart.isMimeType("multipart/*")) {
                Multipart mpart = (Multipart)bodyPart.getContent();
                parseMultipart(mpart,message);
//                System.out.println("1");
            } else if (bodyPart.isMimeType("application/octet-stream")) {
//                if(result==false) {
//                    String disposition = bodyPart.getDisposition();
//
//                    if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {
//                        String fileName = bodyPart.getFileName();//文件名
//                        fileName = new String(fileName.getBytes(), "UTF-8");//文件名编码
//                        InputStream is = bodyPart.getInputStream();//文件内容
//                        try {
//                            Workbook book = Workbook.getWorkbook(is);
//                            //0代表第一个工作表对象
//                            Sheet sheet = book.getSheet(0);
//                            int rows = sheet.getRows();
//                            int cols = sheet.getColumns();
//                            String colname1 = sheet.getCell(3, 0).getContents().trim();//取第四个字段
//                            System.out.println(colname1);//显示字段名
//                            for (int z = 1; z < rows; z++) {
//                                //0代表列数，z代表行数
//                                String name = sheet.getCell(3, z).getContents();//取第四个字段
//                                if (name.contains("0756")) {
//                                    name = name.replace("0756", "");
//                                }
//                                if (name.contains("@jnu.edu.gd"))
//                                    name = name.replace("@jnu.edu.gd", "");
//
//                                if (name.length() == 10) {
//                                    try {
//
//                                        FileWriter fw = new FileWriter("C:\\Users\\hyh\\Desktop\\a.txt", true);
//                                        BufferedWriter bw = new BufferedWriter(fw);
//                                        bw.write(name + "\r\n");// 往已有的文件上添加字符串
//                                        bw.close();
//                                        fw.close();
//
//                                    } catch (Exception e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (is != null)
//                            is.close();
//                    }
//                }
//                result=true;
            }
        }
    }


    public static void main(String[] args) throws Exception {
        EmailUtil.CheckEmail();
    }

}