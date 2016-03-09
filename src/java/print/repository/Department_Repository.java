/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print.repository;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import print.dto.Department;
import print.hibernate.SessionFactorySingleton;
import print.service.DepartmentWithStatInfo;

/**
 *
 * @author apopovkin
 */
public class Department_Repository implements Serializable {

    /**
     * Сохраняет в БД единичный объект. Если в БД существует неудаленный
     * пользователь с указанным коротким именем обновляет информацию, если нет -
     * создает новую строку в БД
     *
     * @param obj пользователь
     */
    public void save(Department obj) {
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
    protected void save(Department obj, Session session) {





        Department dbRes = getDepartment(obj.getDepartment_sn(), false, session);
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
    public void delete(Department obj) {
        obj.setDelete_flag(true);
        save(obj);
    }

    /**
     * Отмена удаления аккаунта. delete_flag присваивается false, объект
     * сохраняется в БД
     *
     * @param obj аккаунт
     */
    public void restore(Department obj) {
        obj.setDelete_flag(false);
        save(obj);
    }

    /**
     * Извлекаем из БД весь список аккаунтов, кроме тех, у которых
     * Delete_flag=true, отсортированный по короткому названию учетной записи
     *
     * @return список аккаунтов
     */
    public List<Department> getAllDepartment(Date dt_beg, Date dt_end, boolean delete_flag) {

        List<Department> dbList;
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        dbList = getAllDepartment(dt_beg, dt_end, delete_flag, session);

        session.getTransaction().commit();
        session.close();

        return dbList;
    }

    protected List<Department> getAllDepartment(Date dt_beg, Date dt_end, boolean delete_flag, Session session) {

        List<Department> dbList;
        if(dt_end==null && dt_beg==null){
            dbList = (List<Department>) session.createQuery(
                "from Department where delete_flag=:delete_flag "
                + "order by department_sn")
                .setParameter("delete_flag", delete_flag).list();
        } else{
            dbList = (List<Department>) session.createQuery(
                "from Department where delete_flag=:delete_flag and "
                + " ((dt_beg<:dt_end and dt_end>:dt_beg) or (dt_beg<:dt_end and dt_end is null)) order by department_sn")
                .setParameter("delete_flag", delete_flag)
                .setDate("dt_beg", dt_beg)
                .setDate("dt_end", dt_end).list();
        }
        return dbList;
    }

    /**
     * Извлекаем из БД нЕУДАЛЕННЫЙ аккаунт с заданным названием
     *
     * @return аккаунт
     */
    public Department getDepartment(String name, boolean delete_flag) {
        Department dbRes;
        SessionFactory sessionFactory = SessionFactorySingleton
                .getConfigHibernate("");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        dbRes = getDepartment(name, delete_flag, session);

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
    protected Department getDepartment(String department_sn, boolean delete_flag, Session session) {
        Department dbRes;

        dbRes = (Department) session
                .createQuery("from Department where delete_flag=:delete_flag and department_sn=:name order by department_sn")
                .setParameter("delete_flag", delete_flag)
                .setParameter("name", department_sn).uniqueResult();

        return dbRes;
    }

    /**
     * Находит кол-во страниц, напечатанных отделом, с начальной даты по
     * конечную
     *
     * @param dep отдел
     * @param begDate начальная дата
     * @param endDate конечная дата
     * @return кол-во страниц и документов поотдельно
     */
    public List<DepartmentWithStatInfo> getStatInfoByDepartment(Date begDate,
            Date endDate) {
        List<DepartmentWithStatInfo> res = new ArrayList<DepartmentWithStatInfo>();
        String qu = "select sum(l.Count_Page) "
                + "from Logger l join l.id_account.all_id_account ac join ac.id_department dep"
                + " WHERE l.delete_flag=0 AND dep.id=:depId";
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

        List<Department> deps = getAllDepartment(null, null, false, session);

        for (Department dep : deps) {

            Query query = session.createQuery(qu).setDate("begDate", begDate)
                    .setDate("endDate", endDate).setLong("depId", dep.getId());
            Long pageCount = (Long) query.uniqueResult();
            if (pageCount != null && pageCount > 0) {
                DepartmentWithStatInfo dws = new DepartmentWithStatInfo();
                dws.setItem(dep);

                dws.getInfo().setPageCount((Long) query.uniqueResult());
                query = session.createQuery(qu1).setDate("begDate", begDate)
                        .setDate("endDate", endDate).setLong("depId", dep.getId());
                dws.getInfo().setDocCount((Long) query.uniqueResult());
                res.add(dws);
            }
        }



        session.getTransaction().commit();
        session.close();

        return res;
        //new list(mother, offspr, mate.name)
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
            Department_Repository rep = new Department_Repository();
            Department dep = new Department();
            Department dep1 = new Department();
            dep.setDepartment_fn("Отдел математики");
            dep1.setDepartment_fn("Отдел управления персонаом");
            dep.setDepartment_sn("АСУ ТП");
            dep1.setDepartment_sn("ПСР");
            rep.save(dep);
            rep.save(dep1);
        }
    }
}
