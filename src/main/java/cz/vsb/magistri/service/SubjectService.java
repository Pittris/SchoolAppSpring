package cz.vsb.magistri.service;

import cz.vsb.magistri.dto.SubjectDto;
import cz.vsb.magistri.entity.SubjectEntity;
import cz.vsb.magistri.mapper.SubjectMapper;
import cz.vsb.magistri.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectMapper subjectMapper;
    public List<SubjectDto> getSubject(){
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for(SubjectEntity subjectEntity : subjectEntities){
            subjectDtos.add(subjectMapper.toDto(subjectEntity));
        }
        return subjectDtos;
    }
    public SubjectDto addSubject(SubjectDto studentDto){
        SubjectEntity subjectEntity = subjectMapper.toEntity(studentDto);
        SubjectEntity savedEntity = subjectRepository.save(subjectEntity);
        return subjectMapper.toDto(savedEntity);
    }
    public SubjectDto getSubject(Integer id){
        SubjectEntity subjectEntity = subjectRepository
                .findById(id).orElseThrow(EntityNotFoundException::new);
        //if(!subjectRepository.existsById(id)){
        //    throw new EntityNotFoundException();
        //}
        //SubjectEntity subjectEntity = subjectRepository.getReferenceById(id);
        return subjectMapper.toDto(subjectEntity);
    }
    public SubjectDto editSubject(Integer id, SubjectDto subjectDto){
        //SubjectEntity subjectToEdit = subjectRepository
        //        .findById(id).orElseThrow(EntityNotFoundException::new);
        if(!subjectRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        SubjectEntity subjectEntity = subjectMapper.toEntity(subjectDto);
        subjectEntity.setId(id);
        SubjectEntity
                savedSubject = subjectRepository.save(subjectEntity);
        return subjectMapper.toDto(savedSubject);
    }
    public SubjectDto deleteSubject(Integer id){
        SubjectEntity subjectToDelete = subjectRepository
                .findById(id).orElseThrow(EntityNotFoundException::new);
        SubjectDto deletedSubject = subjectMapper.toDto(subjectToDelete);
        subjectRepository.delete(subjectToDelete);
        return deletedSubject;
    }
}
