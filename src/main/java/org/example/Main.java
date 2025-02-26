package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Configuration config = new Configuration().configure("config.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true)
        {
            System.out.println("Press 1 for Insert new Employee");
            System.out.println("Press 2 for Display all Employees");
            System.out.println("Press 3 for Search Employee");
            System.out.println("Press 4 for Update Information");
            System.out.println("Press 5 for Remove Employee");
            System.out.println("Press 6 for display employees whose salary is greater than average salary");
            System.out.println("Press 7 for display employees whose salary is less than average salary");
            System.out.println("Press 8 for get total salary city wise ");
            System.out.println("Press 9 for display average age of employee city wise ");
            System.out.println("Press 10 for arrange data ");
            System.out.println("Enter Your Choice ");
            choice = sc.nextInt();

            if(choice == 1)
            {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                NativeQuery query = session.createNativeQuery("insert into employee(name,age,salary) values(:name,:age,:salary)");
                System.out.println("Enter Name ");
                query.setParameter("name",sc.next());
                System.out.println("Enter Age ");
                query.setParameter("age",sc.nextInt());
                System.out.println("Enter Salary ");
                query.setParameter("salary",sc.nextInt());
                query.executeUpdate();
                transaction.commit();
                session.close();
                System.out.println("Record Inserted!..........");
            }
            else if(choice == 2)
            {
                Session session = sessionFactory.openSession();
                NativeQuery query = session.createNativeQuery("select * from employee",Employee.class);
                List<Employee> data = query.getResultList();
                for (Employee item : data)
                {
                    System.out.println(item.toString());
                }
                session.close();
            }
            else if(choice == 3)
            {
                Session session = sessionFactory.openSession();
                System.out.println("Press 1 for Search by SNO");
                System.out.println("Press 2 for Search by Name");
                System.out.println("Press 3 for Search by Age");
                System.out.println("Press 4 for Search by Salary");
                System.out.println("Enter your choice ");
                int choice2 = sc.nextInt();

                if(choice2 == 1)
                {
                    NativeQuery query = session.createNativeQuery("select * from employee where sno=:sno",Employee.class);
                    System.out.println("Enter Employee SNO that you want to search");
                    query.setParameter("sno",sc.nextInt());
                    try
                    {
                        Employee employee = (Employee) query.getSingleResult();
                        System.out.println(employee.toString());
                    }
                    catch (NoResultException ob)
                    {
                        System.out.println("No such Employee on that index");
                    }
                }
                else if(choice2 == 2)
                {
                    NativeQuery query = session.createNativeQuery("select * from employee where name=:name",Employee.class);
                    System.out.println("Enter Employee Name that you want to search ");
                    query.setParameter("name",sc.next());
                    try
                    {
                        Employee employee = (Employee) query.getSingleResult();
                        System.out.println(employee.toString());
                    }
                    catch (NonUniqueResultException ob)
                    {
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                    }
                    catch (NoResultException ob)
                    {
                        System.out.println("Employee not found");
                    }

                }
                else if(choice2 == 3)
                {
                    NativeQuery query = session.createNativeQuery("select * from employee where age=:age",Employee.class);
                    System.out.println("Enter Employee age that you want to search");
                    query.setParameter("age",sc.nextInt());
                    try
                    {
                        Employee employee = (Employee) query.getSingleResult();
                        System.out.println(employee.toString());
                    }
                    catch (NonUniqueResultException ob)
                    {
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                    }
                    catch (NoResultException ob)
                    {
                        System.out.println("Employee not found");
                    }
                }
                else if(choice2 == 4)
                {
                    NativeQuery query = session.createNativeQuery("select * from employee where salary=:salary", Employee.class);
                    System.out.println("Enter Employee salary that you want to search");
                    query.setParameter("salary",sc.nextInt());
                    try
                    {
                        Employee employee = (Employee) query.getSingleResult();
                        System.out.println(employee.toString());
                    }
                    catch (NonUniqueResultException ob)
                    {
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                    }
                    catch (NoResultException ob)
                    {
                        System.out.println("Employee not found");
                    }
                }
                session.close();
            }
            else if(choice == 4)
            {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                System.out.println("Enter Employee SNO that you want to Update");
                NativeQuery query = session.createNativeQuery("select * from employee where sno=:sno",Employee.class);
                query.setParameter("sno",sc.nextInt());

                try
                {
                    Employee employee = (Employee) query.getSingleResult();
                    System.out.println(employee.toString());
                    System.out.println("Press 1 for change Name ");
                    System.out.println("Press 2 for change Age ");
                    System.out.println("Press 3 for change Salary ");
                    System.out.println("Press 4 for change City ");
                    System.out.println("Enter your choice ");
                    int choice2 = sc.nextInt();

                    if(choice2 == 1)
                    {
                        System.out.println("Enter new Name ");
                        employee.setName(sc.next());
                    }
                    else if(choice2 == 2)
                    {
                        System.out.println("Enter new Age ");
                        employee.setAge(sc.nextInt());
                    }
                    else if(choice2 == 3)
                    {
                        System.out.println("Enter new Salary ");
                        employee.setSalary(sc.nextInt());
                    }
                    else if(choice2 == 4)
                    {
                        System.out.println("Enter new City ");
                        employee.setCity(sc.next());
                    }
                    query = session.createNativeQuery("update employee set name=:name,age=:age,salary=:salary,city=:city where sno=:sno", Employee.class);
                    query.setParameter("name",employee.getName());
                    query.setParameter("age",employee.getAge());
                    query.setParameter("salary",employee.getSalary());
                    query.setParameter("city",employee.getCity());
                    query.setParameter("sno",employee.getSno());
                    query.executeUpdate();
                    transaction.commit();
                    System.out.println("Record Updated!........");
                }
                catch (NoResultException ob)
                {
                    System.out.println("No Employee found on that SNO ");
                }
                session.close();
            }
            else if(choice == 5)
            {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                System.out.println("Enter Employee SNO that you want to Remove ");
                NativeQuery query = session.createNativeQuery("select * from employee where sno=:sno", Employee.class);
                query.setParameter("sno",sc.nextInt());

                try
                {
                    Employee employee = (Employee) query.getSingleResult();
                    query = session.createNativeQuery("delete from employee where sno=:sno", Employee.class);
                    query.setParameter("sno",employee.getSno());
                    query.executeUpdate();
                    transaction.commit();
                    System.out.println(employee.getName()+" is Removed!.....");
                }
                catch (NoResultException ob)
                {
                    System.out.println("No Employee found on that SNO ");
                }
                session.close();
            }
            else if(choice == 6)
            {
                Session session = sessionFactory.openSession();
                NativeQuery query = session.createNativeQuery("select avg(salary) from employee");
                Object data = query.getSingleResult();
                System.out.println("Average Salary - "+data);
                query = session.createNativeQuery("select * from employee where salary>(select avg(salary) from employee)",Employee.class);
                List<Employee> data2 = query.getResultList();
                for(Employee item : data2)
                {
                    System.out.println(item.toString());
                }
                session.close();
            }
            else if(choice == 7)
            {
                Session session = sessionFactory.openSession();
                NativeQuery query = session.createNativeQuery("select avg(salary) from employee");
                Object data = query.getSingleResult();
                System.out.println("Average salary - "+data);
                query = session.createNativeQuery("select * from employee where salary<(select avg(salary) from employee)",Employee.class);
                List<Employee> data2 = query.getResultList();
                for(Employee item : data2)
                {
                    System.out.println(item.toString());
                }
                session.close();
            }
            else if(choice == 8)
            {
                Session session = sessionFactory.openSession();
                NativeQuery query = session.createNativeQuery("select city,sum(salary) from employee group by city");
                List<Object[]> data = query.getResultList();
                System.out.println("City\tTotal Salary");
                for(Object item[] : data)
                {
                    System.out.println(item[0]+"\t"+item[1]);
                }
                session.close();
            }
            else if(choice == 9)
            {
                Session session = sessionFactory.openSession();
                NativeQuery query = session.createNativeQuery("select city,avg(age) from employee group by city");
                List<Object[]> data = query.getResultList();

                for(Object item[] : data)
                {
                    System.out.println(item[0]+"\t"+item[1]);
                }
                session.close();
            }
            else if(choice == 10)
            {
                System.out.println("Press 1 for sort by Name ");
                System.out.println("Press 2 for sort by Age ");
                System.out.println("Press 3 for sort by Salary ");
                System.out.println("Press 4 for sort by SNO");
                System.out.println("Enter your choice ");
                int choice2 = sc.nextInt();

                if(choice2 == 1)
                {
                    System.out.println("Press 1 for sort by Name in ascending order ");
                    System.out.println("Press 2 for sort by Name in descending order ");
                    System.out.println("Enter your choice ");
                    int choice3 = sc.nextInt();

                    if(choice3 == 1)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by name", Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }

                        session.close();
                    }
                    else if(choice3 == 2)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by name desc",Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                        session.close();
                    }
                }
                else if(choice2 == 2)
                {
                    System.out.println("Press 1 for sort by Age in ascending order ");
                    System.out.println("Press 2 for sort by Age in descending order ");
                    System.out.println("Enter your choice ");
                    int choice3 = sc.nextInt();

                    if(choice3 == 1)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by age", Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }

                        session.close();
                    }
                    else if(choice3 == 2)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by age desc",Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                        session.close();
                    }
                }
                else if(choice2 == 3)
                {
                    System.out.println("Press 1 for sort by Salary in ascending order ");
                    System.out.println("Press 2 for sort by Salary in descending order ");
                    System.out.println("Enter your choice ");
                    int choice3 = sc.nextInt();

                    if(choice3 == 1)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by salary", Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }

                        session.close();
                    }
                    else if(choice3 == 2)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by salary desc",Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                        session.close();
                    }
                }
                else if(choice2 == 4)
                {
                    System.out.println("Press 1 for sort by SNO in ascending order ");
                    System.out.println("Press 2 for sort by SNO in descending order ");
                    System.out.println("Enter your choice ");
                    int choice3 = sc.nextInt();

                    if(choice3 == 1)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by sno", Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }

                        session.close();
                    }
                    else if(choice3 == 2)
                    {
                        Session session = sessionFactory.openSession();
                        NativeQuery query = session.createNativeQuery("select * from employee order by sno desc",Employee.class);
                        List<Employee> data = query.getResultList();
                        for(Employee item : data)
                        {
                            System.out.println(item.toString());
                        }
                        session.close();
                    }
                }
            }
        }
    }
}
