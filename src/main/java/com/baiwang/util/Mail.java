package com.baiwang.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
























import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class Mail {
	private static Logger logger=Logger.getLogger(Mail.class.getName());
	//发件人邮箱
	private static String username="573152032@qq.com";
	//邮箱密码
	private static String password="xmyzupjsxaiubfic";
	//发件人昵称
	private static String sender="phineas";
	//系统属性
	private Properties props;
	//邮件会话对象
	private Session session;
	//MIME邮件对象
	private MimeMessage mimeMsg;
	//Multipart对象，邮件内容，标题，附件等内容均添加到其中再生成MimeMessage
	private Multipart mp;
	
	private static Mail instance=null;
	//构造器，初始化系统属性
	public Mail(){
		props=System.getProperties();
		//表示SMTP发送邮件，必须进行身份验证
		props.put("mail.smtp.auth", "true");
		props.put("mail.transport.protocol", "smtp");
		//此处填写SMTP服务器
		props.put("mail.smtp.host", "smtp.qq.com");
		//端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
		props.put("mail.smtp.port", "587");
		//发件邮箱
		props.put("mail.user", "573152032@qq.com");
		// 16位授权码
		props.put("mail.password", "xmyzupjsxaiubfic");
		//建立会话
		session=Session.getDefaultInstance(props);
		session.setDebug(false);
		}
	public static Mail getInstance(){
		if(instance==null){
			instance = new Mail();
		}
		return instance;
	}
	/**
     * 发送邮件
     * @param from 发件人
     * @param to 收件人
     * @param copyto 抄送
     * @param subject 主题
     * @param content 内容
     * @param fileList 附件列表
     * @return
     */
	public boolean sendMail(String[] copyto,File file){
		String from = "573152032@qq.com";
		String to   = "guoshenzhen@baiwang.com";
		String subject = "百望云接口测试";
		String content = "测试结果在附件中请查收";
		boolean success = true;
		try{
			mimeMsg = new MimeMessage(session);
			mp = new MimeMultipart();
			//自定义发件人昵称
			String nick="";
			try{
				nick=javax.mail.internet.MimeUtility.encodeText(sender);
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
			//设置发件人
			mimeMsg.setFrom(new InternetAddress(from,nick));
			//设置收件人
			//if(to != null&&to.length>0){
				//String toListStr = getMailList(to);
				mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			//}
			//设置抄送人
			if(copyto != null&&copyto.length>0){
				String ccListStr = getMailList(copyto);
				mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccListStr));
				
			}
			//设置主体
			mimeMsg.setSubject(subject);
			//设置正文
			BodyPart bp = new MimeBodyPart();
			bp.setContent(content,"text/html;charset=utf-8");
			mp.addBodyPart(bp);
			//设置附件
			if(file!=null){
				//for(int i=0;i<fileList.length;i++){
					bp=new MimeBodyPart();
					FileDataSource fds = new FileDataSource(file);
					bp.setDataHandler(new DataHandler(fds));
					bp.setFileName(MimeUtility.encodeText(fds.getName(),"UTF-8","B"));
					mp.addBodyPart(bp);
					
				//}
			}
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			//发送邮件
			if(props.get("mail.smtp.auth").equals("true")){
				Transport transport = session.getTransport("smtp");
				transport.connect((String)props.get("mail.smtp.host"), (String)props.get("mail.user"),(String)props.get("mail.password"));
				transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
				transport.close();
			}else{
				Transport.send(mimeMsg);
			}
			System.out.println("邮件发送成功");
			
		}catch(MessagingException  e){
			e.printStackTrace();
			success=false;
			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			success=false;
		}
		return success;
	}
	private String getMailList(String[] mailArray){
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if(mailArray!=null&&length<2){
			toList.append(mailArray[0]);
		}else{
			for(int i=0;i<length;i++){
				toList.append(mailArray[i]);
				if(i!=(length-1)){
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}
	public void send(File file) throws MessagingException{
		// 创建Properties 类用于记录邮箱的一些属性
		final Properties props = new Properties();
		// 表示SMTP发送邮件，必须进行身份验证
		props.put("mail.smtp.auth", "true");
		//此处填写SMTP服务器
		props.put("mail.smtp.host", "smtp.qq.com");
		//端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
		props.put("mail.smtp.port", "587");
		// 此处填写你的账号
		props.put("mail.user", "573152032@qq.com");
		// 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", "xmyzupjsxaiubfic");
     // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
     // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress("guoshenzhen@baiwang.com");
        message.setRecipient(RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("测试邮件");

        // 设置邮件的内容体
       // message.setContent("这是一封测试邮件", "text/html;charset=UTF-8");
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();         
        //附件地址
        String affix=file.getAbsolutePath();
        //附件名称
        String affixName="report";
        //   设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText("邮件的具体内容在附件中");
        multipart.addBodyPart(contentPart);
        //添加附件
        BodyPart messageBodyPart= new MimeBodyPart();
        DataSource source = new FileDataSource(affix);
        //添加附件的内容
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(file.getName());
      //  String fileName = source.getFile().getName();
        //添加附件的标题
        //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
    //  sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
      //  messageBodyPart.setFileName("UTF-8"+enc.encode(affixName.getBytes("UTF-8"))+"?=");
        multipart.addBodyPart(messageBodyPart);
      
      
        //将multipart对象放到message中
        message.setContent(multipart);
        //保存邮件
        message.saveChanges();

       

        // 最后当然就是发送邮件啦
        Transport.send(message);
		
	}
	//附件为参数，抄送人为参数
	public static void main(String[] args) throws MessagingException{
		
		/*// 创建Properties 类用于记录邮箱的一些属性
		final Properties props = new Properties();
		// 表示SMTP发送邮件，必须进行身份验证
		props.put("mail.smtp.auth", "true");
		//此处填写SMTP服务器
		props.put("mail.smtp.host", "smtp.qq.com");
		//端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
		props.put("mail.smtp.port", "587");
		// 此处填写你的账号
		props.put("mail.user", "573152032@qq.com");
		// 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", "xmyzupjsxaiubfic");
     // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
     // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人的邮箱
        InternetAddress to = new InternetAddress("guoshenzhen@baiwang.com");
        message.setRecipient(RecipientType.TO, to);

        // 设置邮件标题
        message.setSubject("测试邮件");

        // 设置邮件的内容体
       // message.setContent("这是一封测试邮件", "text/html;charset=UTF-8");
        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart multipart = new MimeMultipart();         
        //附件地址
        String affix="Excel/test.xlsx";
        //附件名称
        String affixName="测试报告";
        //   设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText("邮件的具体内容在此");
        multipart.addBodyPart(contentPart);
        //添加附件
        BodyPart messageBodyPart= new MimeBodyPart();
        DataSource source = new FileDataSource(affix);
        //添加附件的内容
        messageBodyPart.setDataHandler(new DataHandler(source));
        //添加附件的标题
        //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        messageBodyPart.setFileName("UTF-8"+enc.encode(affixName.getBytes())+"?=");
        multipart.addBodyPart(messageBodyPart);
      
      
        //将multipart对象放到message中
        message.setContent(multipart);
        //保存邮件
        message.saveChanges();

       

        
        Transport.send(message);
		*/
	
	}
	}


