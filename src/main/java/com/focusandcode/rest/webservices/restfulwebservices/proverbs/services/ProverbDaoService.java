package com.focusandcode.rest.webservices.restfulwebservices.proverbs.services;

import com.focusandcode.rest.webservices.restfulwebservices.proverbs.domain.Proverb;
import com.focusandcode.rest.webservices.restfulwebservices.proverbs.exceptions.ProverbAlreadyExistException;
import com.focusandcode.rest.webservices.restfulwebservices.proverbs.exceptions.ProverbNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProverbDaoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProverbDaoService.class);

    private static List<Proverb> Proverbs = new ArrayList<>();
    private static int proverbCount = 5;

    private static String proverbMeaning = "It is wrestling with the pains of hunger."
            + "His forhead is not wrinkled through age, but through hunger, anxiety and the hard knocks of life"
            + "Experience counts more than years.";

    {
        Proverbs.add(new Proverb("1",
                "Kujinga mpala ke bukulu. I musongo wa nzala walwa nao.",
                "Wrinkling the forhead does not make one grown up.",
                proverbMeaning,
                "",
                "",
                23,
                0,
                null));
        Proverbs.add(new Proverb("1",
                "Kujinga mpala ke bukulu. I musongo wa nzala walwa nao.",
                "Wrinkling the forhead does not make one grown up.",
                proverbMeaning,
                "",
                "",
                23,
                0,
                null));
        Proverbs.add(new Proverb("1", "Kujinga mpala ke bukulu. I musongo wa nzala walwa nao.", "Wrinkling the forhead does not make one grown up.", proverbMeaning,
        "", "",
                23,
                0,
                null));
        Proverbs.add(new Proverb("1", "Kujinga mpala ke bukulu. I musongo wa nzala walwa nao.", "Wrinkling the forhead does not make one grown up.", proverbMeaning , "", "", 23, 0, null));
        Proverbs.add(new Proverb("1", "Kujinga mpala ke bukulu. I musongo wa nzala walwa nao.", "Wrinkling the forhead does not make one grown up.",   proverbMeaning,
        "", "",
                23,
                0,
                null));
    }

    public static Proverb save(Proverb Proverb) {
        try{
            Proverb oldProverb = findOne(Proverb.getId());
            throw new ProverbAlreadyExistException(String.format("Proverb with id-%s already exist", oldProverb.getId()));
        } catch (ProverbNotFoundException ue) {
            Proverb.setId(String.valueOf(++proverbCount));
            Proverbs.add(Proverb);
        }
        return Proverb;
    }

    public static List<Proverb> findAll() {
        return Proverbs;
    }

    public static Proverb findOne(String id) {
        for(Proverb proverb : Proverbs) {
            if (proverb.getId().equals(id)) {
                return proverb;
            }
        }
        throw new ProverbNotFoundException("id-" + id);
    }

    public static Proverb delete(String id) {
        Proverb deletedProverb = findOne(id);
        boolean remove = Proverbs.remove(deletedProverb);
        return deletedProverb;
    }
}
