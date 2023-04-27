package com.example.springboot3todoapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.springboot3todoapplication.services.TodoItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.example.springboot3todoapplication.models.TodoItem;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class TodoFormController {

    @Autowired
    private TodoItemService todoItemService;

    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "new-todo-item";
    }

    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model) {

        if (todoItem.getDescription() == null  || todoItem.getClaimType() == null || todoItem.getClaimType().isEmpty() ) {
            model.addAttribute("error", "Field cannot be empty");
            return "new-todo-item"; // return to the form page to display the error message
        }
        if(result.hasErrors() || result.hasGlobalErrors() || result.hasFieldErrors()){
            model.addAttribute("error", "Error! Note Starred fields cannot be empty!");
            return "new-todo-item";
        }


        TodoItem item = new TodoItem();
       item.setDescription(todoItem.getDescription());
       // item.setApprovedAmount(todoItem.getDescription());
        item.setClaimMonth(todoItem.getClaimMonth());
        item.setClaimYear(todoItem.getClaimYear());
        item.setClaimType(todoItem.getClaimType());
        item.setIsComplete(todoItem.getIsComplete());


        todoItemService.save(todoItem);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        todoItemService.delete(todoItem);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        model.addAttribute("todo", todoItem);
        return "edit-todo-item";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model) {


        TodoItem item= todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

       try{
           if(result.hasErrors()){
               throw new IllegalArgumentException("Error!");
           }
           String approvedAmount = todoItem.getApprovedAmount();
           if (approvedAmount == null || approvedAmount.isEmpty()) {
            throw new IllegalArgumentException("Approved amount cannot be null or empty.");
        }
        item.setApprovedAmount(approvedAmount);
    } catch (IllegalArgumentException ex) {
        model.addAttribute("error", ex.getMessage());
        return "edit-todo-item";
    }


        item.setIsComplete(todoItem.getIsComplete());


      /*  if (Integer.parseInt(todoItem.getApprovedAmount()) > Integer.parseInt( todoItem.getDescription())){
            model.addAttribute("error2", "Approved amount cannot be greater than claimed amount!");
            return "edit-todo-item";
        }*/

       // item.setDescription(todoItem.getDescription());
        System.out.println("approved amount-------------------------------->"+item.getApprovedAmount());
        //item.setApprovedAmount(todoItem.getApprovedAmount());
        //System.out.println("approved amount-------------------------------->"+item.getApprovedAmount());


        todoItemService.save(item);

        return "redirect:/";
    }
}
