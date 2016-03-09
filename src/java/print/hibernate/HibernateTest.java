/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.hibernate;

import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import print.dto.Account;
import print.dto.Account_Department;
import print.dto.Autorization;
import print.dto.Department;
import print.dto.Logger;
import print.dto.Print;
import print.dto.Service;
import print.repository.Department_Repository;

/**
 *
 * @author apopovkin
 */
public class HibernateTest {

    /**
     * @param args the command line arguments
     */
 
      public static void main(String[] args) throws Exception  {
		
		Date ft1=new Date();

                Account account = new Account();
                account.setAccount_sn("Вася1");
               /* Account account1 = new Account();
                account1.setAccount_sn("Вася1");
               /* Account account2 = new Account();
                account2.setAccount_sn("Вася3");*/
                
                Department department = new Department();
                department.setDepartment_sn("Петя1");
               /* Department department1 = new Department();
                department1.setDepartment_sn("erer2");*/
                
                Account_Department account_Department=new Account_Department();
		account_Department.setId_account(account);
                account_Department.setId_department(department);
                
              /*  Account_Department account_Department1=new Account_Department();
		account_Department1.setId_account(account1);
                account_Department1.setId_department(department1);
                
                Account_Department account_Department2=new Account_Department();
		account_Department2.setId_account(account2);
                account_Department2.setId_department(department1);*/
                
                account.getAll_id_account().add(account_Department);
             /*   account1.getAll_id_account().add(account_Department1);
                account2.getAll_id_account().add(account_Department1);*/
                department.getAll_id_department().add(account_Department);
              /*  department1.getAll_id_department().add(account_Department1);
                department1.getAll_id_department().add(account_Department2);*/
                
                Print print= new Print();
                print.setName("Ксерокс");
                
                Service service= new Service();
                service.setModel_katreg("Ксерокс_катреж");
                service.setId_print(print);
                
                Logger logger=new Logger();
                logger.setCode("307");
                logger.setCount_Page(4);
                logger.setId_account(account);
                logger.setId_print(print);
                
                account.getAll_id_account_logger().add(logger);
                print.getId_print().add(logger);
                print.getId_print_service().add(service);
                
                
                Autorization autorization=new Autorization();
                autorization.setLogin("admin");
                autorization.setPassword("admin");
                autorization.setId_account(account);
                //account.getAll_id_account_autorization().add(autorization);
                
                
                
                
                
               
                String config="";

                SessionFactory sessionFactory= SessionFactorySingleton.getConfigHibernate(config);
                
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(account);
                session.save(department);
                session.save(account_Department);
                
                session.save(autorization);
                session.save(print);
                session.save(logger);
                session.save(service);
                
                session.getTransaction().commit();
		session.close();
                Department_Repository gg= new Department_Repository() ;           
            /*    List<Department> list_department=gg.select();  
                for(Department f:list_department){
                System.out.println("1==="+f.getDepartment_sn());
                }*/
                
		
                Date ft2=new Date();
		System.out.println("1==="+ft1);
		System.out.println("2==="+ft2);
		
	}
}
