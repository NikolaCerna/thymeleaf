package sk.ukf.mvc3.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.ukf.mvc3.entity.Employee;
import sk.ukf.mvc3.service.EmployeeService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Value("${app.job-titles}")
    private String jobTitleOptions;

    @Value("${app.employment-types}")
    private String employmentTypeOptions;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employees/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        addFormOptions(model);
        return "employees/form";
    }

    @PostMapping
    public String createEmployee(@ModelAttribute("employee") Employee employee) {
        employee.setId(null);
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        addFormOptions(model);
        return "employees/form";
    }

    @PostMapping("/{id}")
    public String updateEmployee(@PathVariable Integer id,
                                 @ModelAttribute("employee") Employee employee) {
        employeeService.findById(id);
        employee.setId(id);
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    // Hodnoty z application.properties posielame do Thymeleaf formulara.
    private void addFormOptions(Model model) {
        model.addAttribute("jobTitles", Arrays.asList(jobTitleOptions.split("\\s*,\\s*")));
        model.addAttribute("employmentTypes", Arrays.asList(employmentTypeOptions.split("\\s*,\\s*")));
    }
}
