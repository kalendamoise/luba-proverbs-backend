package com.focusandcode.rest.webservices.lubaproverbs.proverbs.services;

import com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain.Proverb;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.exceptions.ProverbAlreadyExistException;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.exceptions.ProverbNotFoundException;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.repository.ProverbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class ProverbDaoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProverbDaoService.class);
    private final ProverbRepository proverbRepository;

    @Autowired
    public ProverbDaoService(ProverbRepository proverbRepository) {
        Assert.notNull(proverbRepository, "proverbRepository cannot be null");
        this.proverbRepository = proverbRepository;
    }

    public Proverb save(Proverb proverb) {
        try{
            Proverb oldProverb = findOne(proverb.getProverbId());
            throw new ProverbAlreadyExistException(String.format("Proverb with id-%s already exist", oldProverb.getProverbId()));
        } catch (ProverbNotFoundException ue) {
            Proverb save = this.proverbRepository.save(proverb);
            return save;
        }
    }

    public List<Proverb> findAll() {
        return this.proverbRepository.findAll();
    }

    public Proverb findOne(String id) {

        Optional<Proverb> proverb = this.proverbRepository.findById(id);
        if (proverb.isPresent() ) {
            return proverb.get();
        }
        throw new ProverbNotFoundException("id-" + id);
    }

    public Proverb delete(String id) {
        Proverb deletedProverb = findOne(id);
        this.proverbRepository.delete(deletedProverb);
        return deletedProverb;
    }
}
