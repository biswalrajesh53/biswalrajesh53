package userDatabase;
import java.util.*;
import java.sql.*;
import java.util.regex.*;
public class DataEnterypage {

	public static void main(String[] args)throws Exception
	{
       Client obj=new Client();
        obj.jdbcConn();
      Scanner input=new Scanner(System.in);
        System.out.println("do u have account already (yes/no)");
		String alr=input.next();
		if(alr.equals("no"))
		{
		obj.Sign_up();
		}
		else if(alr.equals("yes"))
		{
		obj.sign_in();
		  int t=0;
		  while(t!=1) {
		  System.out.println("what u want to do (update/view) goto for next");
		    String s=input.next();
		    if(s.equalsIgnoreCase("update")) {
		     obj.updateAdmin();}
		    else if( s.equalsIgnoreCase("view"))
		    {
		    	obj.viewAdmin();
		    }
		    else if(s.equalsIgnoreCase("goto"))
		    {
		    	t=1;
		    }
		  }
		}
        int n=0;
        while(n!=7) {
        System.out.println("enter what to do");
        System.out.println("1: inputdata ||  2: view || 3: update || 4: delete || 5: seekdata || 6: cleanAlldata || 7 : for exit");
        
         n=input.nextInt();
     switch(n)
     {
     case 1:  obj.input();
       obj.insertData();
       break;
     case 2:
       obj.view();
       break;
     case 3:  
       obj.update();
       break;
     case 4:
    	 obj.deleteData();
    	 break;
     case 5:
    	 obj.seekData();
    	 break;
     case 6:
    	 obj.clean();
    	 break;
    	
     } System.out.println("thanks");
        }
	}

}
class Client
{
	 private String name;
	 private String email;
	 private long ph_no;
	 private String pwd; //as address 
	 Connection conn=null;
	 Scanner sc=new Scanner(System.in);
	 public void input()
	   {
		   int c=0;
		   System.out.println("enter name");
		   name =sc.next();
		   System.out.println("enter email"); //----------------------------------
		   while(c!=1)
		   	{ System.out.println("please enter valid email : ");
		   	 email=sc.next();
		     	String s="[a-z]{3,4}[0-9]*{4,8}@gmail\\.com"; //regx *for or
			   Pattern ok=Pattern.compile(s);
			   Matcher ok1=ok.matcher(email);
			   while(ok1.find())
				{
					System.out.println(ok1.group());
					 if(ok1.group().equals(email))
					 {
					 	System.out.println("ok");
					 	c=1;
					 }
				}
		   	   
			 //  if(email.contains("@gmail.com"))
			   if(c==1)
			   {
				  // c=1;
				   System.out.println("valid ");
			   }
			   else
			   {
				   System.out.println("plz enter valid email ");
			   }
		   	}  //--------------------------------------------------------------------

		   int ok=0;
		 while(ok!=1) {  
		   System.out.println("enter phone number");
		   ph_no=sc.nextLong();
		      long ch=ph_no;
		      int count=0;
		     while(ch!=0)
		     {
		    	ch=ch/10;
		    	count++;
		     }
		      if(count ==10)
		      {
		    	  System.out.println("valid no"); 
		    	  ok=1;
		      }
		      else
		      {
		    	  System.out.println("invalid no. ");
		      }
		 }
		   
		   
		    System.out.println("enter address");
		   pwd=sc.next();
	   }
	   public void jdbcConn() throws SQLException
	   {
		    conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","Chandan");
	   }
	   public void insertData() throws SQLException
	   {
		    conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","Chandan");
		   PreparedStatement st=conn.prepareStatement("insert into user_data values(?,?,?,?)");
		     st.setString(1, name);
		     st.setString(2, email);
		     st.setLong(3, ph_no);
		     st.setString(4, pwd);
		     conn.commit();
		     st.executeUpdate();
		     //conn.close();
	   }
	 //---------------------++++++++++++++++++++++++++++++++++++++++++++++++++   Sign_up
	 public void Sign_up() throws SQLException
	 {
		 int c=0;
	   	Statement st=conn.createStatement();
	   	System.out.println("please enter admin name : ");
	   	String nm=sc.next();
	   	String eml=null;
	   while(c!=1)
	   	{ System.out.println("please enter valid email : ");
	   	 eml=sc.next();
	     	String s="[a-z]{3,4}[0-9]*{4,8}@gmail\\.com"; //regx *for or
		   Pattern ok=Pattern.compile(s);
		   Matcher ok1=ok.matcher(eml);
		   while(ok1.find())
			{
				System.out.println(ok1.group());
				 if(ok1.group().equals(eml))
				 {
				 	System.out.println("ok");
				 	c=1;
				 }
				 else
				 {
				 	System.out.println("invalid email");
				 	break;
				 }
			}
		   if(c==1)
		   {
			   System.out.println("valid ");
		   }
		   else
		   {
			   System.out.println("plz enter valid email ");
		   }
		   
	   	}  

	   	System.out.println("please enter valid 10digit ph_no. : ");
	   	Long ph=sc.nextLong();
	   	System.out.println("please password : ");
	   	String pwd=sc.next();
	   String execute=String.format("insert into admin values ('%s','%s','%d','%s')",nm,eml,ph,pwd);
	   	st.executeUpdate(execute);
	   	st.executeUpdate("commit");
	 }
	 void sign_in() throws SQLException ////////------------------------------  sign in
	 {
	     Statement st1=conn.createStatement();
	     int c=0;
	     System.out.println("come to sign_in");
	     while(c!=1)
	     {
	 	System.out.println("please enter regd. email : ");
	 	String eml1=sc.next();
	 	System.out.println("plz enter password");
	 	String pw=sc.next();
	 	
	 	ResultSet r=st1.executeQuery("select email,pwd from admin ");
	 	
	 	while(r.next())
	 	{
	 		if(r.getString(1).equals(eml1)&&r.getString(2).equals(pw))
	 		{
	 			c=1;
	 		}
	 		else
	 		{
	 			c=0;
	 		}
	 	}
	 	if(c==1)
	 	{
	 		System.out.println("entered");
	 	}
	 	else
	 	{
	 		System.out.println("pleas enter valid email ...");
	 	}
	     }
	 }
	 public void viewAdmin() throws SQLException ///-------------------------------------------- viewAdmin
	   {
		   PreparedStatement st1=conn.prepareStatement("select * from admin");
		   ResultSet rs=st1.executeQuery();
		   if(rs.next()==true) 
		   {
			   while(rs.next())
		     {
				   System.out.println("here is ur data ...");
				   System.out.println();
			   System.out.println("name : "+rs.getString(1));
			   System.out.println("email : "+rs.getString(2));
			   System.out.println("ph_no : "+rs.getLong(3));
			   System.out.println("pwd : "+rs.getString(4));
		     }
		   }
		   else
		   {
			   System.out.println("don't have any data .");
		   }
	   }
	 //------update admin data
	 public void updateAdmin() throws SQLException//----------------------------------------------- updateAdmin
	   {
		   System.out.println("please enter what r u want to update .");
		   System.out.println("name : email : ph number : pwd");
			 String name=sc.next();
			 String updt1=null;
			 if(name.equalsIgnoreCase("name"))
			 {
				 updt1="name";
			 }
			 else if(name.equalsIgnoreCase("email"))
			 {
				updt1="email"; 
			 }
			 else if(name.equalsIgnoreCase("ph number"))
			 {
				 updt1="ph_no";
			 }
			 else if(name.equalsIgnoreCase("pwd"))
			 {
				 updt1="pwd";
			 }
			 PreparedStatement st=conn.prepareStatement("update admin set "+updt1+"=?  ");
				 while(true) 
				{
				System.out.println("plz enter the  update details ");
				
				if(updt1.equalsIgnoreCase("ph_no"))
				{
				int number=sc.nextInt();
				  st.setInt(1, number);
				  conn.commit();
				  st.executeUpdate();
				}
				else
				{
					String str=sc.next();
					st.setString(1, str);
					conn.commit();
				  st.executeUpdate();
				}
				 
				  System.out.println("if want to stop updating plz type stop");
				  System.out.println("otherwise go on");
				  String stop=sc.next();
				  if(stop.equals("stop"))
				  {
					  break;
				  }
				}
	   }
	 //--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  ------------------- view
	   public void view() throws SQLException
	   {
		   //Statement st1=conn.createStatement();
		   PreparedStatement st1=conn.prepareStatement("select * from user_data");
		   ResultSet rs=st1.executeQuery();
		   if(rs.next()==true) 
		   {
			   while(rs.next())
		     {
				   System.out.println("here is ur data ...");
				   System.out.println();
			   System.out.println("name : "+rs.getString(1));
			   System.out.println("email : "+rs.getString(2));
			   System.out.println("ph_no : "+rs.getLong(3));
			   System.out.println("address : "+rs.getString(4));
		     }
		   }
		   else
		   {
			   System.out.println("don't have any data .");
		   }
	   }
	   public void update() throws SQLException //------------------------------------- update
	   {
		   System.out.println("please enter what r u want to update .");
		   System.out.println("name : email : ph number : address");
			 String name=sc.next();
			 String updt=null;
			 if(name.equalsIgnoreCase("name"))
			 {
				 updt="name";
			 }
			 else if(name.equalsIgnoreCase("email"))
			 {
				updt="email"; 
			 }
			 else if(name.equalsIgnoreCase("ph number"))
			 {
				 updt="ph_no";
			 }
			 else if(name.equalsIgnoreCase("address"))
			 {
				 updt=pwd;
			 }
			 PreparedStatement st=conn.prepareStatement("update user_data set "+updt+"=? where name=? ");
				 while(true) 
				{System.out.println("plz enter the  name  ");
					 String nam=sc.next();
				System.out.println("plz enter the  update details ");
				
				if(updt.equalsIgnoreCase("ph_no"))
				{
				int number=sc.nextInt();
				  st.setInt(1, number);
				  st.setString(2, nam);
				  conn.commit();
				  st.executeUpdate();
				}
				else
				{
					String str=sc.next();
					st.setString(1, str);
					st.setString(2, nam);
					conn.commit();
				  st.executeUpdate();
				}
				 
				  System.out.println("if want to stop updating plz type stop");
				  System.out.println("otherwise go on");
				  String stop=sc.next();
				  if(stop.equals("stop"))
				  {
					  break;
				  }
				}
	   }
	   public void deleteData() throws SQLException //-------------------------------------  deleteData
	   {
		   System.out.println("plz entered NAME :: which data u want to delete");
		   PreparedStatement st=conn.prepareStatement("delete from user_data where name=?");
		   String nm=sc.next();
		   st.setString(1, nm);
		   conn.commit();
		   st.executeUpdate();
		   System.out.println("delete success that row");
	   }
	   public void clean() throws SQLException //------------------------------------------------- clean
	   {
		   System.out.println("want to clear all data..(yes/no)");
		   String en=sc.next();
		   if(en.equalsIgnoreCase("yes"))
		   {
			   PreparedStatement st=conn.prepareStatement("truncate table user_data"); 
			   System.out.println("cleaned ...**");
			   conn.commit();
			   st.executeUpdate();
		   }
	   }
	   public void seekData() throws SQLException//---------------------------------------------------- seekData
	   {
		   System.out.println("plz entered the name which u  find ");
		   String nm1=sc.next();
		   PreparedStatement stm=conn.prepareStatement("select * from user_data where name=?");
		   stm.setString(1,nm1);
		  ResultSet rs= stm.executeQuery();
		  while(rs.next())
		   {
		    	
	 System.out.println("name : "+rs.getString(1)+"  email : "+rs.getString(2)+"  ph : "+rs.getLong(3)+"  address : "+rs.getString(4));
		    }
		    	
		 
	   }
}
