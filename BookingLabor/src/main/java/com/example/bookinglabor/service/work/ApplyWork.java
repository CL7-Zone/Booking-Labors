package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.ApplyMapper;
import com.example.bookinglabor.model.Apply;
import com.example.bookinglabor.model.Post;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.ApplyRepo;
import com.example.bookinglabor.repo.PostRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.service.ApplyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplyWork implements ApplyService {

    private ApplyRepo applyRepo;
    private PostRepo postRepo;
    private UserRepo userRepo;

    @Override
    public List<Apply> findAppliesByUserAccountId(Long user_id) {
        List<Apply> applies = applyRepo.findAppliesByUserAccountId(user_id);
        return applies.stream().map(
                ApplyMapper::mapToApply)
            .collect(Collectors.toList());
    }

    @Override
    public List<Apply> findAppliesByPostId(Long POST_ID) {

        List<Apply> applies = applyRepo.findAppliesByPostId(POST_ID);

        return applies.stream().map(
                ApplyMapper::mapToApply)
            .collect(Collectors.toList());
    }

    @Override
    public int countAppliesByPostId(Long postId) {
        return applyRepo.countAppliesByPostId(postId);
    }

    @Override
    public int countAppliesByUserAccountIdAndPostId(Long userId, Long postId) {
        return applyRepo.countAppliesByUserAccountIdAndPostId(userId, postId);
    }

    @Override
    public List<Apply> findAll() {
        List<Apply> applies = applyRepo.findAll();
        return applies.stream().map(
                        ApplyMapper::mapToApply)
                .collect(Collectors.toList());
    }

    @Override
    public List<Apply> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Apply> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Apply> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Apply entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Apply> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Apply> S save(S entity) {



        return null;
    }

    @Override
    public <S extends Apply> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Apply> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Apply> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Apply> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Apply> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Apply getOne(Long aLong) {
        return null;
    }

    @Override
    public Apply getById(Long aLong) {
        return null;
    }

    @Override
    public Apply getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Apply> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Apply> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Apply> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Apply> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Apply> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Apply> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Apply, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }



}
