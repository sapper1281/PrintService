package print.repository;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import print.dto.Logger;
import print.dto.Print;
import print.hibernate.SessionFactorySingleton;

/**
 * Работа с БД для объектов принтер.
 * 
 * @author VVolgina
 * 
 */
public class PrinterRepository {

	/**
	 * ССохраняет в БД единичный объект. Если в БД существует неудаленный
	 * принтер с указанным названием обновляет информацию, если нет - создает
	 * новую строку в БД
	 * 
	 * @param obj
	 *            принтер
	 */

	public void save(Print obj) {
		SessionFactory sessionFactory = SessionFactorySingleton
				.getConfigHibernate("");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		save(obj, session);

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Сохраняет в БД единичный объект. Если в БД существует неудаленный принтер
	 * с указанным названием обновляет информацию, если нет - создает новую
	 * строку в БД
	 * 
	 * @param obj
	 *            притнтер
	 * @param session
	 *            сессия
	 */

	protected void save(Print obj, Session session) {

		Print dbRes = getPrint(obj.getName(), session);
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
	 * Удаление принтера. delete_flag присваивается True, объект сохраняется в
	 * БД
	 * 
	 * @param obj
	 *            принтер
	 */

	public void delete(Print obj) {
		obj.setDelete_flag(true);
		save(obj);
	}

	/**
	 * Отмена удаления принтера. delete_flag присваивается false, объект
	 * сохраняется в БД
	 * 
	 * @param obj
	 *            принтер
	 */

	public void restore(Print obj) {
		obj.setDelete_flag(true);
		save(obj);
	}

	/**
	 * Извлекаем из БД весь список принтеров, кроме тех, у которых
	 * Delete_flag=true, отсортированный по назанию
	 * 
	 * @return список принтеров
	 */

	public List<Print> getAllPrint() {
		List<Print> dbList;
		SessionFactory sessionFactory = SessionFactorySingleton
				.getConfigHibernate("");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		dbList = (List<Print>) session.createQuery(
				"from Print where delete_flag=0 order by name").list();

		session.getTransaction().commit();
		session.close();

		return dbList;
	}

	/**
	 * Извлекаем из БД нЕУДАЛЕННЫЙ принтер с заданным названием
	 * 
	 * @return принтер
	 */

	public Print getPrint(String name) {
		Print dbRes;
		SessionFactory sessionFactory = SessionFactorySingleton
				.getConfigHibernate("");

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		dbRes = getPrint(name, session);

		session.getTransaction().commit();
		session.close();

		return dbRes;
	}

	/**
	 * Извлекаем из БД неудаленный принтер с заданным названием Работа
	 * происходит с уже созданной сессией. Если необходимо начать/завершить
	 * транзакцию - это делается вне метода
	 * 
	 * @return принтер
	 */

	protected Print getPrint(String name, Session session) {
		Print dbRes;

		dbRes = (Print) session
				.createQuery("from Print where delete_flag=0 and name=:name")
				.setParameter("name", name).uniqueResult();

		return dbRes;
	}

	/**
	 * тестирование
	 * 
	 * @author VVolgina
	 * 
	 */

	public static class Tester {
		/**
		 * @param args
		 * @throws ParseException
		 * @throws IOException
		 * @throws IllegalArgumentException
		 */
		public static void main(String[] args) throws IllegalArgumentException,
				IOException, ParseException {
			// TODO Auto-generated method stub
			PrinterRepository rep = new PrinterRepository();

			Print pr = new Print();
			pr.setInvent_num("66");
			pr.setName("newPrint");
			rep.save(pr);

			Print pr1 = new Print();
			pr1.setInvent_num("77");
			pr1.setName("newPrint");
			rep.save(pr1);

			/*
			 * Logger log = new Logger(); log.setCode("307");
			 * log.setDelete_flag(false); log.setDocument("��������111");
			 * log.setId(1); log.setDt_print(new Date());
			 */
			/* rep.save(log); */
			/*PrinterParser pars = new PrinterParser();
			List<Logger> list = pars.parse("d:\\res.csv");
			for (Logger logger : list) {
				rep.save(logger.getId_print());
			}*/
		}

	}
}
