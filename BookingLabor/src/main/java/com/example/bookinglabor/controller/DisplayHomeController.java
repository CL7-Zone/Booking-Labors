package com.example.bookinglabor.controller;
import com.example.bookinglabor.repo.PostRepo;
import com.example.bookinglabor.service.*;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.LaborRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Getter
@Setter
@Controller
public class DisplayHomeController {

    private CategoryJobService categoryJobService;
    private LaborService laborService;
    private JobService jobService;
    private JobDetailService jobDetailService;
    private JobDetailRepo jobDetailRepo;
    private LaborRepo laborRepo;
    private PostService postService;

    //interface này có bean là countComponent, countComponent2, countComponent3
    // tương dương new countComponent();, countComponent2();, countComponent3();
    @Qualifier("load")
    private CountService countService;
    @Qualifier("load2")
    private CountService countService2;
    @Qualifier("load3")
    private CountService countService3;

    @Autowired//inject
    public DisplayHomeController(CategoryJobService categoryJobService, LaborService laborService, JobService jobService, JobDetailService jobDetailService, JobDetailRepo jobDetailRepo, LaborRepo laborRepo, CountService countService, CountService countService2, CountService countService3, PostService postService) {
        this.categoryJobService = categoryJobService;
        this.laborService = laborService;
        this.jobService = jobService;
        this.jobDetailService = jobDetailService;
        this.jobDetailRepo = jobDetailRepo;
        this.laborRepo = laborRepo;
        this.countService = countService;
        this.countService2 = countService2;
        this.countService3 = countService3;
        this.postService = postService;
    }

    @GetMapping("/")
    private String index(Model model, Pageable pageable,
                         @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                         @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();
        List<Labor> labors = laborService.findAllLabors();
        List<Job> jobs = jobService.findAllJobs();
        List<JobDetail> jobDetails = jobDetailService.findAllJobDetails();
        Page<JobDetail> jobDetailPage = jobDetailRepo.findAll(PageRequest.of(pageNumber, size));
        Page<Labor> laborPage = laborRepo.findAll(PageRequest.of(pageNumber, size));
        Long countJob = categoryJobService.countJob();
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;
        List<Post> posts = postService.findAllPosts();

        model.addAttribute("posts",posts);
        model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);
        model.addAttribute("categoryJobs", categoryJobs);
        model.addAttribute("jobDetails", jobDetails);
        model.addAttribute("labors", labors);
        model.addAttribute("jobs", jobs);
        model.addAttribute("jobDetailList", jobDetailPage.getContent());
        model.addAttribute("pages", new int[jobDetailPage.getTotalPages()]);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("laborList", laborPage.getContent());
        model.addAttribute("pageLabors", new int[laborPage.getTotalPages()]);

        return "home/index";
    }

    @GetMapping("/category-job/{id}")
    public String show(@PathVariable("id") Long id, Model model){

        CategoryJob categoryJob = categoryJobService.findCategoryJobById(id);
        Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;

        System.out.println("CategoryJob: " + categoryJob);
        System.out.println("CategoryJob ID: " + categoryJob.getId());

        Long jobCount = countJobsByCategoryJobFunction.apply(categoryJob.getId());
        System.out.println("Số công việc của danh mục: " + jobCount);

        model.addAttribute("categoryJob", categoryJob);
        model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);

        return "home/show";
    }

    @GetMapping("/guest/search/jobByLabor")
    private String search(@RequestParam("nameJob") String nameJob, Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        if(nameJob != null){

            Page<JobDetail> jobDetailPage = jobDetailService.findJobDetailsByNameJob(nameJob, PageRequest.of(pageNumber, size));
            Page<Labor> laborPage = laborRepo.findAll(PageRequest.of(pageNumber, size));
            Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;
            List<Post> posts = postService.findAllPosts();

            model.addAttribute("posts",posts);
            model.addAttribute("jobDetailList", jobDetailPage.getContent());
            model.addAttribute("pages", new int[jobDetailPage.getTotalPages()]);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("laborList", laborPage.getContent());
            model.addAttribute("pageLabors", new int[laborPage.getTotalPages()]);
            model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);
        }
        return "home/index";
    }


    @GetMapping("/guest/search-job")
    private String searchJob(@RequestParam("name_job") String nameJob, Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        if(nameJob != null){

            Page<JobDetail> jobDetailPage = jobDetailRepo.findAll(PageRequest.of(pageNumber, size));
            Page<Labor> laborPage = laborRepo.findAll(PageRequest.of(pageNumber, size));
            Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;
            List<Job> jobs = jobService.findJobsByNameJobContaining(nameJob);
            List<Post> posts = postService.findAllPosts();

            model.addAttribute("posts",posts);
            model.addAttribute("jobs", jobs);
            model.addAttribute("jobDetailList", jobDetailPage.getContent());
            model.addAttribute("pages", new int[jobDetailPage.getTotalPages()]);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("laborList", laborPage.getContent());
            model.addAttribute("pageLabors", new int[laborPage.getTotalPages()]);
            model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);
        }
        return "home/index";
    }


    @GetMapping("/guest/filter-price-asc")
    private String filterPriceAsc(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                          @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        Page<JobDetail> jobDetailPage = jobDetailService.findAllByOrderByJobPriceAsc(PageRequest.of(pageNumber, size));
        Page<Labor> laborPage = laborRepo.findAll(PageRequest.of(pageNumber, size));
        Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;
        List<Post> posts = postService.findAllPosts();

        model.addAttribute("posts",posts);
        model.addAttribute("jobDetailList", jobDetailPage.getContent());
        model.addAttribute("pages", new int[jobDetailPage.getTotalPages()]);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("laborList", laborPage.getContent());
        model.addAttribute("pageLabors", new int[laborPage.getTotalPages()]);
        model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);

        return "home/index";
    }


    @GetMapping("/guest/filter-price-desc")
    private String filterPriceDesc(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber,
                               @RequestParam(value = "size", required = false, defaultValue = "6") int size){

        Page<JobDetail> jobDetailPage = jobDetailService.findAllByOrderByJobPriceDesc(PageRequest.of(pageNumber, size));
        Page<Labor> laborPage = laborRepo.findAll(PageRequest.of(pageNumber, size));
        Function<Long, Long> countJobsByCategoryJobFunction = categoryJobService::countJobsByCategoryJob;
        List<Post> posts = postService.findAllPosts();

        model.addAttribute("posts",posts);
        model.addAttribute("jobDetailList", jobDetailPage.getContent());
        model.addAttribute("pages", new int[jobDetailPage.getTotalPages()]);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("laborList", laborPage.getContent());
        model.addAttribute("pageLabors", new int[laborPage.getTotalPages()]);
        model.addAttribute("countJobsByCategoryJob", countJobsByCategoryJobFunction);

        return "home/index";
    }




}
