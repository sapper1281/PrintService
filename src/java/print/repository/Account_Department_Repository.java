/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import print.dto.Account;
import print.dto.Account_Department;
import print.dto.Department;
import print.hibernate.SessionFactorySingleton;

/**
 *
 * @author apopovkin
 */
public class Account_Department_Repository  implements Serializable{
    
     
    	
	/**
	 * Сохраняет в БД единичный объект. Если в БД существует неудаленный
	 * пользователь с указанным коротким именем обновляет информацию, если нет - создает
	 * новую строку в БД
	 * 
	 * @param obj
	 *            пользователь
	 */

	public void save(Account_Department obj) {
		SessionFactory sessionFactory = SessionFactorySingleton
				.getConfigHibernate("");

		Session session = sessionFactory.openSession();
		session.beginTransaction();
               /* Account account=new Account();
                account.save(obj.getId_account(), session);*/
		save(obj, session);

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Сохраняет в БД единичный объект. Если в БД существует неудаленный
	 * пользователь с указанным коротким именем обновляет информацию, если нет - создает
	 * новую строку в БД
	 * 
	 * @param obj
	 *            пользователь
	 * @param session
	 *            сессия
	 */

	protected void save(Account_Department obj, Session session) {

            if(obj.getId_account()!=null){
            Account_Repository rep=new Account_Repository();
            obj.setId_account(rep.getAccount(obj.getId_account().getAccount_sn(), false,session));
            }
        
            if(obj.getId_department()!=null){
            Department_Repository rep=new Department_Repository();
            obj.setId_department(rep.getDepartment(obj.getId_department().getDepartment_sn(), false,session));
            }
            
		Account_Department dbRes = getAccount_Department(obj.getId_account(),obj.getId_department(),false, session);
		if (dbRes != null) {
			if (!dbRes.equalsToDB(obj)) {
				obj.cloneForBD(dbRes);
				session.update(dbRes);
			}
		} else {
			session.save(obj);
		}

	}

	/**
	 * Удаление аккаунта. delete_flag присваивается True, объект сохраняется в
	 * БД
	 * 
	 * @param obj
	 *            принтер
	 */

	public void delete(Account_Department obj) {
		obj.setDelete_flag(true);
		save(obj);
	}

	/**
	 * Отмена удаления аккаунта. delete_flag присваивается false, объект
	 * сохраняется в БД
	 * 
	 * @param obj
	 *            аккаунт
	 */

	public void restore(Account_Department obj) {
		obj.setDelete_flag(false);
		save(obj);
	}

	/**
	 * Извлекаем из БД весь список аккаунтов, кроме тех, у которых
	 * Delete_flag=true, отсортированный по короткому названию учетной записи
	 * 
	 * @return список аккаунтов
	 */

	public List<Account_Department> getAllAccount_Department(Date dt_beg, Date dt_end,boolean delete_flag) {
           
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            
		List<Account_Department> dbList;
		SessionFactory sessionFactory = SessionFactorySingleton
				.getConfigHibernate("");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		dbList = (List<Account_Department>) session.createQuery(
				"from Account_Department where delete_flag=:delete_flag and "
                        + " ((dt_beg<:dt_end and dt_end>:dt_beg) or (dt_beg<:dt_end and dt_end is null)) order by id")
                        .setParameter("delete_flag", delete_flag)
                        .setDate("dt_beg", dt_beg)
                        .setDate("dt_end", dt_end).list();

		session.getTransaction().commit();
		session.close();

		return dbList;
	}

	/**
	 * Извлекаем из БД нЕУДАЛЕННЫЙ аккаунт с заданным названием
	 * 
	 * @return аккаунт
	 */

	public Account_Department getAccount_Department(Account account,Department department,boolean delete_flag) {
		Account_Department dbRes;
		SessionFactory sessionFactory = SessionFactorySingleton
				.getConfigHibernate("");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		dbRes = getAccount_Department(account,department,delete_flag, session);

		session.getTransaction().commit();
		session.close();

		return dbRes;
	}

	/**
	 * Извлекаем из БД неудаленный аккаунт с заданным названием Работа
	 * происходит с уже созданной сессией. Если необходимо начать/завершить
	 * транзакцию - это делается вне метода
	 * 
	 * @return аккаунт
	 */

	protected Account_Department getAccount_Department(Account account,Department department,boolean delete_flag, Session session) {
		Account_Department dbRes;

		dbRes = (Account_Department) session
				.createQuery("from Account_Department where delete_flag=:delete_flag and id_account=:id_account and id_department=:id_department order by id")
                        .setParameter("delete_flag", delete_flag)
                        .setParameter("id_account", account)
                        .setParameter("id_department", department).uniqueResult();

		return dbRes;
	}
    
   
}