package pl.javastart.zadanie2201;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @RequestMapping("/")
    public String mainPage(Model model){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("SELECT e FROM Worker e");
        List<Worker> workerList = query.getResultList();

        model.addAttribute("workerList",workerList);

        return "index";
    }

    @GetMapping("/info/{id}")
    public String workerInfo(@PathVariable("id") long id,
                             Model model
                                                         ){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Worker worker = entityManager.find(Worker.class,id);

        entityManager.getTransaction().commit();

        model.addAttribute("worker",worker);

        return "worker";
    }

    @GetMapping("/dodaj")
    public String addWorker(Model model){

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        model.addAttribute("worker", new Worker());

        entityManager.getTransaction().begin();

        TypedQuery query = entityManager.createQuery("SELECT c FROM Company c", Company.class);

        List<Company> resultList = query.getResultList();

        model.addAttribute("companies", resultList);

        return "add";
    }

    @PostMapping("/dodaj")
    public String workerAdded(Worker worker, Model model){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        TypedQuery queryCompany = entityManager.createQuery("SELECT c FROM Company c", Company.class);
        List<Company> resultList = queryCompany.getResultList();

        entityManager.getTransaction().commit();

        Company companyToUpdate = null;

        for (Company company : resultList) {
            if(company.getId() == worker.getCompanyAsLong()){
                companyToUpdate = company;
            }
        }
        worker.setCompany(companyToUpdate);

        entityManager.getTransaction().begin();

        try {
            entityManager.persist(worker);

            entityManager.getTransaction().commit();
        } catch (NumberFormatException e){
            return "error";
        }

        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("SELECT e FROM Worker e");
        List<Worker> workerList = query.getResultList();
        entityManager.getTransaction().commit();


        model.addAttribute("workerList",workerList);

        return "lista";
    }

    @GetMapping("/edycja/{id}")
    public String edit(@PathVariable("id") long id,
                       Model model){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Worker worker = entityManager.find(Worker.class,id);

        entityManager.getTransaction().commit();

        model.addAttribute("worker",worker);

        entityManager.getTransaction().begin();

        TypedQuery query = entityManager.createQuery("SELECT c FROM Company c", Company.class);

        List<Company> resultList = query.getResultList();

        model.addAttribute("companies", resultList);


        return "edit";
    }

    @PostMapping("/edycja/{id}")
    public String workerUpdated(Worker worker, Model model,
                                @PathVariable("id") long id){

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        TypedQuery queryCompany = entityManager.createQuery("SELECT c FROM Company c", Company.class);
        List<Company> resultList = queryCompany.getResultList();

        entityManager.getTransaction().commit();

        Company companyToUpdate = null;

        for (Company company : resultList) {
            if(company.getId() == worker.getCompanyAsLong()){
                companyToUpdate = company;
            }
        }

        entityManager.getTransaction().begin();

        try {
            Worker workerToUpdate = entityManager.find(Worker.class,id);

            workerToUpdate.setFirstName(worker.getFirstName());
            workerToUpdate.setLastName(worker.getLastName());
            workerToUpdate.setSalary(worker.getSalary());
            workerToUpdate.setWorkingSince(worker.getWorkingSince());
            workerToUpdate.setPESEL(worker.getPESEL());
            workerToUpdate.setCompany(companyToUpdate);


            entityManager.getTransaction().commit();

        } catch (NumberFormatException e){
            return "error";
        }

        entityManager.getTransaction().begin();

        Query queryWorker = entityManager.createQuery("SELECT e FROM Worker e");
        List<Worker> workerList = queryWorker.getResultList();

        entityManager.getTransaction().commit();

        model.addAttribute("workerList",workerList);


        return "lista";
    }



}
