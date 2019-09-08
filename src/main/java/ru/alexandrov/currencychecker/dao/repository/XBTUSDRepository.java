package ru.alexandrov.currencychecker.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alexandrov.currencychecker.dao.model.XBTUSDModel;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface XBTUSDRepository extends JpaRepository<XBTUSDModel, Long> {
    List<XBTUSDModel> findAllByDeltaAfter(int delta);
}
