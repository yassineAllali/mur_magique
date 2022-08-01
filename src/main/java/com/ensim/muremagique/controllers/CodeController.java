package com.ensim.muremagique.controllers;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.services.CodeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/codes")
public class CodeController {

    private CodeService codeService;

    public CodeController(CodeService codeService)
    {
        this.codeService = codeService;
    }

    @GetMapping
    public List<Code> getAll()
    {
        return codeService.getAll();
    }

    @GetMapping("/{id}")
    public Code getCode(@PathVariable Long id)
    {
        System.out.println(id);
        return codeService.getCode(id);
    }

    @PostMapping()
    public Code createCode(@RequestParam() MultipartFile code)
    {
        return codeService.uploadCode(code);
    }
}
