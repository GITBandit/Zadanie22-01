package pl.javastart.zadanie2201;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.List;

@Controller
public class CompanyController {

    @Autowired
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;



    @GetMapping("/company")
    private String company(Model model){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        TypedQuery query = entityManager.createQuery("SELECT c FROM Company c", Company.class);

        List<Company> resultList = query.getResultList();

        model.addAttribute("companies", resultList);

        return "company";
    }

    @GetMapping("/company/{id}")
    private String companyInfo(@PathVariable("id") long id, Model model){

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Company company = entityManager.find(Company.class, id);

        model.addAttribute("company", company);

        return "company_info";
    }

}
