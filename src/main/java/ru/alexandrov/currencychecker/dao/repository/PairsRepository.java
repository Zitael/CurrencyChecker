package ru.alexandrov.currencychecker.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandrov.currencychecker.dao.model.PairsModel;

import java.util.List;

@Repository
public interface PairsRepository extends JpaRepository<PairsModel, Long> {
    List<PairsModel> findAllByCurrencyAndDeltaAfter(String currency, float delta);
    List<PairsModel> findAllByCurrencyAndTimestampBetween(String currency, Long from, Long to);
    Page<PairsModel> findAllByCurrency(String currency, Pageable pageable);
    PairsModel findFirstByCurrencyOrderByIdDesc(String currency);
}
