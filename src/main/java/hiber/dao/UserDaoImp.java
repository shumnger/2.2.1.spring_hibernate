package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
      if (user.getCar() !=null) {
         sessionFactory.getCurrentSession().save(user.getCar());
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory
              .getCurrentSession().createQuery("from User");
      return query.getResultList();
   }
   @Override
   public void add(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   public List<Car> listCars() {
      TypedQuery<Car> query = sessionFactory
              .getCurrentSession().createQuery("from Car");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String car_model, int car_series) {
       TypedQuery<User> query = sessionFactory.getCurrentSession()
               .createQuery("from User where car.model = :m and car.series = :s");
       query.setParameter("m", car_model);
       query.setParameter("s", car_series);
       return query.getSingleResult();
   }

}


