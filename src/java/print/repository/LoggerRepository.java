package print.repository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

import print.dto.Department;
import print.dto.Logger;
import print.dto.Print;
import print.hibernate.SessionFactorySingleton;
import print.service.StatInfo;

/**
 * Работа с БД для логера
 * 
 * @author VVolgina
 * 
 */
public class LoggerRepository {

	 public void save(Logger log) {
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(log, session);

        session.getTransaction().commit();
        session.close();
    }

    protected void save(Logger log, Session session) {
        if (log.getId_print() != null) {
            PrinterRepository rep = new PrinterRepository();
            log.setId_print(rep.getPrint(log.getId_print().getName(), session));
        }
        if (log.getId_account() != null) {
            Account_Repository rep = new Account_Repository();
            log.setId_account(rep.getAccount(log.getId_account()
                    .getAccount_sn(), false, session));
        }
        if (log.getId() == 0) {
            session.save(log);
        } else {
            session.update(log);
        }
    }

    public void delete(Logger log) {
        log.setDelete_flag(true);
        save(log);
    }

    public void save(List<Logger> list) {
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        save(list, session);

        session.getTransaction().commit();
        session.close();
    }

    protected void save(List<Logger> list, Session session) {
        for (Logger l : list) {
            save(l, session);
        }
    }

    /**
     * Получаем дату последней сохраненной в БД записи логера
     *
     * @return дату последней сохраненной в БД записи логера
     */
    public Date getLastNoteDate() {
        // Находим в БД дату последней записи логера
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List res = session
                .createCriteria(Logger.class)
                .setProjection(
                Projections.projectionList().add(
                Projections.max("dt_print"))).list();

        session.getTransaction().commit();
        session.close();

        Date maxTime;
        // если там были записи, искомая дата у нас уже есть
        if (res.size() > 0 && res.get(0) != null) {
            maxTime = (Date) res.get(0);
        } else {
            // если записей в БД не было, то в качестве начальной даты берем
            // 01.01.2001
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                maxTime = format.parse("2001-01-01");
            } catch (ParseException e) {
                maxTime = new Date();
            }
        }
        return maxTime;
    }

    /**
     * Находит кол-во страниц, напечатанных отделом, начиная с заданной даты
     *
     * @param dep отдел
     * @param begDate начальная дата
     * @return кол-во страниц
     */
    public int getStatInfoByDepartment(Department dep, Date begDate) {
        return getStatInfoByDepartment(dep, begDate, null);
    }

    /**
     * Находит кол-во страниц, напечатанных отделом, с начальной даты по
     * конечную
     *
     * @param dep отдел
     * @param begDate начальная дата
     * @param endDate конечная дата
     * @return кол-во страниц
     */
    public int getStatInfoByDepartment(Department dep, Date begDate,
            Date endDate) {
        int res = 0;
        String qu = "select sum(l.Count_Page) "
                + "from Logger l"
                + " WHERE l.delete_flag=0 AND l.id_account.all_id_account.id_department.id=:depId";
        if (begDate != null) {
            qu += " AND l.dt_print>=:begDate";
        }

        if (endDate != null) {
            qu += " AND l.dt_print<=:endDate";
        }
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(qu).setDate("begDate", begDate)
                .setDate("endDate", endDate).setLong("depId", dep.getId());
        res = (Integer) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return res;
        //new list(mother, offspr, mate.name)
    }

    /**
     * Находит кол-во страниц, напечатанных на принтере, начиная с заданной даты
     *
     * @param pr принтре
     * @param begDate начальная дата
     * @return кол-во страниц
     */
    public long getCountOfPagesByPrinter(Print pr, Date begDate) {
        return getCountOfPagesByPrinter(pr, begDate, null);
    }

    /**
     * Находит кол-во страниц, напечатанных на принтере, с начальной даты по
     * конечную
     *
     * @param pr принтер
     * @param begDate начальная дата
     * @param endDate конечная дата
     * @return кол-во страниц
     */
    public long getCountOfPagesByPrinter(Print pr, Date begDate,
            Date endDate) {
        long res = 0;
        String qu = "select sum(l.Count_Page) "
                + "from Logger l"
                + " WHERE l.delete_flag=0 AND l.id_print.id=:prId";
        if (begDate != null) {
            qu += " AND l.dt_print>=:begDate";
        }

        if (endDate != null) {
            qu += " AND l.dt_print<=:endDate";
        }
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(qu).setLong("prId", pr.getId());
        if (begDate != null) {
            query.setDate("begDate", begDate);
        }

        if (endDate != null) {
            query.setDate("endDate", endDate);
        }

        res = (Long) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return res;
        //new list(mother, offspr, mate.name)
    }

    /**
     * Находит кол-во страниц, напечатанных с начальной даты по конечную
     *
     * @param begDate начальная дата
     * @param endDate конечная дата
     * @return кол-во страниц и документов
     */
    public StatInfo getAllStatInfo(Date begDate,
            Date endDate) {
        StatInfo res = new StatInfo();
        String qu = "select sum(l.Count_Page) "
                + "from Logger l"
                + " WHERE l.delete_flag=0";
        if (begDate != null) {
            qu += " AND l.dt_print>=:begDate";
        }

        if (endDate != null) {
            qu += " AND l.dt_print<=:endDate";
        }
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(qu).setDate("begDate", begDate)
                .setDate("endDate", endDate);
        res.setPageCount((Long) query.uniqueResult());

        qu = qu.replaceFirst("sum", "count");
        query = session.createQuery(qu).setDate("begDate", begDate)
                .setDate("endDate", endDate);
        res.setDocCount((Long) query.uniqueResult());

        session.getTransaction().commit();
        session.close();

        return res;
        //new list(mother, offspr, mate.name)
    }

    /**
     * Находит кол-во страниц, напечатанных начиная с заданной даты
     *
     * @param begDate начальная дата
     * @return кол-во страниц и документов
     */
    public StatInfo getAllStatInfo(Date begDate) {
        return getAllStatInfo(begDate, null);
    }

    public List<Logger> getLogsForAccount(long idAcc, Date begDate,
            Date endDate) {
        List<Logger> res = new ArrayList<Logger>();
        String qu = "select l "
                + "from Logger l"
                + " WHERE l.delete_flag=0 AND l.id_account=:idAcc";
        if (begDate != null) {
            qu += " AND l.dt_print>=:begDate";
        }

        if (endDate != null) {
            qu += " AND l.dt_print<=:endDate";
        }
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query query = session.createQuery(qu).setDate("begDate", begDate)
                .setDate("endDate", endDate).setLong("idAcc", idAcc);
        res=(ArrayList<Logger>)query.list();

        session.getTransaction().commit();
        session.close();

        return res;
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
            LoggerRepository rep = new LoggerRepository();
            Print testPr = new Print();
            testPr.setId(4);
            System.out.println(rep.getCountOfPagesByPrinter(testPr, null));

            /*
             * Logger log = new Logger(); log.setCode("307");
             * 
             * log.setDelete_flag(false); log.setDocument("документ");
             */

            /*
             * Logger log = new Logger(); log.setCode("307");
             * log.setDelete_flag(false); log.setDocument("документ111");
             * log.setId(1); log.setDt_print(new Date());
             */
            /* rep.save(log); */
            /*PrinterParser pars = new PrinterParser();
             List<Logger> list = pars.parse("d:\\res.csv");
             rep.save(list);*/
        }
    }
}
