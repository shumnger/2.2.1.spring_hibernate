package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

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
      return  sessionFactory.getCurrentSession().createQuery("FROM User").getResultList();
   }
   @Override
   public void add(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   public List<Car> listCars() {
      return sessionFactory.getCurrentSession().createQuery("FROM Car").getResultList();
   }

   @Override
   public User getUserByCarModelAndSeries(String carModel, int carSeries) {
        return sessionFactory.getCurrentSession()
               .createQuery("from User where car.model = :model and car.series = :series",User.class)
               .setParameter("model", carModel)
               .setParameter("series", carSeries)
               .getSingleResult();
   }

}