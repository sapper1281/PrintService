/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.repository;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import print.dto.Account;
import print.hibernate.SessionFactorySingleton;
import print.service.UserWithStatInfo;

/**
 *
 * @author apopovkin
 */
public class Account_Repository implements Serializable {

    /**
     * Сохраняет в БД единичный объект. Если в БД существует неудаленный
     * пользователь с указанным коротким именем обновляет информацию, если нет -
     * создает новую строку в БД
     *
     * @param obj пользователь
     */
    public void save(Account obj) {
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(obj, session);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Сохраняет в БД единичный объект. Если в БД существует неудаленный
     * пользователь с указанным коротким именем обновляет информацию, если нет -
     * создает новую строку в БД
     *
     * @param obj пользователь
     * @param session сессия
     */
    protected void save(Account obj, Session session) {





        Account dbRes = getAccount(obj.getAccount_sn(), false, session);
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
     * @param obj принтер
     */
    public void delete(Account obj) {
        obj.setDelete_flag(true);
        save(obj);
    }

    /**
     * Отмена удаления аккаунта. delete_flag присваивается false, объект
     * сохраняется в БД
     *
     * @param obj аккаунт
     */
    public void restore(Account obj) {
        obj.setDelete_flag(false);
        save(obj);
    }

    /**
     * Извлекаем из БД весь список аккаунтов, отсортированный по ФИО
     * пользователя
     *
     * @return список аккаунтов
     */
    public List<Account> getAllAccount() {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        List<Account> dbList;
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        dbList = (List<Account>) session.createQuery(
                "from Account order by account_surname,account_name,account_patronymic").list();

        session.getTransaction().commit();
        session.close();

        return dbList;
    }

    /**
     * Извлекает из БД список пользователей, учетные записи которых активны в
     * заданный период, с указанным статусом удаления
     *
     * @param dt_beg начало периода
     * @param dt_end конец периода
     * @param delete_flag флаг удаления
     * @return список пользователей
     */
    public List<Account> getAllAccount(Date dt_beg, Date dt_end, boolean delete_flag) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        List<Account> dbList;
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        dbList = (List<Account>) session.createQuery(
                "from Account where delete_flag=:delete_flag and "
                + " ((dt_beg<:dt_end and dt_end>:dt_beg) or (dt_beg<:dt_end and dt_end is null)) order by account_surname,account_name,account_patronymic")
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
    public Account getAccount(String account_sn, boolean delete_flag) {
        Account dbRes;
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        dbRes = getAccount(account_sn, delete_flag, session);

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
    protected Account getAccount(String account_sn, boolean delete_flag, Session session) {
        Account dbRes;

        dbRes = (Account) session
                .createQuery("from Account where delete_flag=0 and account_sn=:name")
                .setParameter("name", account_sn).uniqueResult();

        return dbRes;
    }

    /**
     * Извлекаем из БД неудаленный аккаунт с заданным названием Работа
     * происходит с уже созданной сессией. Если необходимо начать/завершить
     * транзакцию - это делается вне метода
     *
     * @return аккаунт
     */
    protected Account getAccount(long id) {
        Account dbRes;
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        dbRes = (Account) session.get(Account.class, id);

        session.getTransaction().commit();
        session.close();

        return dbRes;
    }

    /**
     * Находит кол-во страниц, напечатанных пользоваиеоями отдела, с начальной
     * даты по конечную
     *
     * @param dep ID отдела
     * @param begDate начальная дата
     * @param endDate конечная дата
     * @return кол-во страниц и документов поюзерно
     * @deprecated 
     */
    public List<UserWithStatInfo> getStatInfoByAccountOld(long depID, Date begDate,
            Date endDate) {
        List<UserWithStatInfo> res = new ArrayList<UserWithStatInfo>();

        String qu = "select sum(l.Count_Page) "
                + "from Logger l"
                + " WHERE l.delete_flag=0 AND l.id_account=:depId";
        if (begDate != null) {
            qu += " AND l.dt_print>=:begDate";
        }

        if (endDate != null) {
            qu += " AND l.dt_print<=:endDate";
        }

        String qu1 = qu.replaceFirst("sum", "count");

        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query;
        if (depID != -100) {
            String quUsers = "select a "
                    + "from Account a join a.all_id_account ac join ac.id_department dep"
                    + " WHERE a.delete_flag=0 AND dep.id=:depId";
            query = session.createQuery(quUsers).setLong("depId", depID);
        } else {
            String quUsers = "select a "
                    + "from Account a join a.all_id_account ac join ac.id_department dep"
                    + " WHERE a.delete_flag=0";
            query = session.createQuery(quUsers);
        }

        List<Account> accs = (ArrayList<Account>) query.list();

        for (Account ac : accs) {
            UserWithStatInfo dws = new UserWithStatInfo();
            dws.setItem(ac);
            query = session.createQuery(qu).setDate("begDate", begDate)
                    .setDate("endDate", endDate).setLong("depId", ac.getId());
            Long reult = (Long) query.uniqueResult();
            query = session.createQuery(qu1).setDate("begDate", begDate)
                    .setDate("endDate", endDate).setLong("depId", ac.getId());
            Long resDoc = (Long) query.uniqueResult();
            if (reult != null && resDoc != null) {
                dws.getInfo().setPageCount(reult);

                dws.getInfo().setDocCount(resDoc);
                res.add(dws);
            }
        }



        session.getTransaction().commit();
        session.close();

        return res;
        //new list(mother, offspr, mate.name)
    }

    /**
     * Находит кол-во страниц, напечатанных пользоваиеоями отдела, с начальной
     * даты по конечную
     *
     * @param dep ID отдела
     * @param begDate начальная дата
     * @param endDate конечная дата
     * @return кол-во страниц и документов поюзерно
     */
    public List<UserWithStatInfo> getStatInfoByAccount(long depID, Date begDate,
            Date endDate) {
        List<UserWithStatInfo> res;

        String qu = "select new print.service.UserWithStatInfo(l.id_account.id, l.id_account.account_name, l.id_account.account_surname, l.id_account.account_patronymic, sum(l.Count_Page), count(l.Count_Page)) "
                + "from Logger l join l.id_account.all_id_account ac join ac.id_department dep"
                + " WHERE l.delete_flag=0";
        if (depID != -100) {
            qu += " AND dep.id=:depId";
        }

        if (begDate != null) {
            qu += " AND l.dt_print>=:begDate";
        }

        if (endDate != null) {
            qu += " AND l.dt_print<=:endDate";
        }
        qu += " GROUP BY l.id_account.id, l.id_account.account_name, l.id_account.account_surname, l.id_account.account_patronymic";


        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(qu);
        if (depID != -100) {
            query.setLong("depId", depID);
        }
        query.setDate("begDate", begDate)
                .setDate("endDate", endDate);

        res = (ArrayList<UserWithStatInfo>)query.list();

        session.getTransaction().commit();
        session.close();

        for (UserWithStatInfo userWithStatInfo : res) {
            if(userWithStatInfo.getDocCount()<=0){
                res.remove(userWithStatInfo);
            }
        }
        return res;
        //new list(mother, offspr, mate.name)
    }
    
    public static class Tester {

        /**
         * @param args
         * @throws ParseException
         * @throws IOException
         * @throws IllegalArgumentException
         */
        public static void main(String[] args) throws ParseException {
            Account_Repository rep=new Account_Repository();
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date beg=format.parse("01.02.2013");
            Date end=format.parse("01.04.2013");
            List<UserWithStatInfo> statInfoByAccountNew = rep.getStatInfoByAccount(5, beg, end);
            for (UserWithStatInfo  userWithStatInfo: statInfoByAccountNew) {
                System.out.println(userWithStatInfo.getItem().getFIO()+" "+userWithStatInfo.getDocCount()+" "+userWithStatInfo.getPageCount());
            }
        }
    }
}