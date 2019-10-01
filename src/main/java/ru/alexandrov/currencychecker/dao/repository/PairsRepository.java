package ru.alexandrov.currencychecker.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alexandrov.currencychecker.dao.model.PairsModel;

import java.util.List;

@Repository
public interface PairsRepository extends JpaRepository<PairsModel, Long> {
    @Query(value = "SELECT * FROM Pairs WHERE currency = ?1 AND delta >= ?2 ORDER BY id DESC LIMIT ?3", nativeQuery = true)
    List<PairsModel> findAllByCurrencyAndDeltaAfter(String currency, float delta, int counts);
    List<PairsModel> findAllByCurrencyAndTimestampBetween(String currency, Long from, Long to);
    PairsModel findFirstByCurrencyOrderByIdDesc(String currency);
}
