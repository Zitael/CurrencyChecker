package ru.alexandrov.currencychecker.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;

@Repository
public interface XBTUSDRepository extends JpaRepository<XBTUSDModel, Long> {
}
