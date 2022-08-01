package com.ensim.muremagique.services;

import com.ensim.muremagique.entities.Code;
import com.ensim.muremagique.repositories.CodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CodeService {

    private CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public List<Code> getAll() {
        return codeRepository.findAll();
    }

    public Code getCode(Long id) {
        return codeRepository.findById(id).orElseThrow(() -> new NotFoundException("Code not foud"));
    }

    public Code createCode(String path) {
        Code code = new Code(path);
        codeRepository.save(code);
        return code;
    }

    public Code uploadCode(MultipartFile file) {

        return createCode("fileDownloadUri");
    }
}
