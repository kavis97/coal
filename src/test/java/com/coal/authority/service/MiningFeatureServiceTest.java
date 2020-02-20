package com.coal.authority.service;

import authority.data.*;
import authority.queries.MultipleQuery;
import authority.queries.Query;
import authority.queries.SingleQuery;
import authority.service.MiningFeatureService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MiningFeatureServiceTest {

    private MiningFeatureService service = new MiningFeatureService();
    Logger logger = Logger.getLogger("MiningFeatureServiceTest");

    @Test
    public void shouldReturnEmptyCollection_ForNullEntries() {
        Assertions.assertTrue(() -> service.process(null, null).isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_ForEmptyEntries() {
        Assertions.assertTrue(() -> service.process(Collections.emptyList(), null).isEmpty());
    }

    @Test
    public void shouldReturnSameCollection_ForNullQuery() {
        Collection<Entry> entries = getEntries();
        Collection<Entry> result = service.process(entries, null);
        Assertions.assertTrue(() -> result.size() == entries.size());
        Assertions.assertEquals(entries, result);
    }

    @Test
    public void shouldReturnSameCollection_ForNullQueryBuilder() {
        Collection<Entry> entries = getEntries();
        Query singleQuery = new SingleQuery(null);
        Collection<Entry> result = service.process(entries, singleQuery);
        Assertions.assertTrue(() -> result.size() == entries.size());
        Assertions.assertEquals(entries, result);
    }

    @Test
    public void shouldApplyGivenQueryAndReturnFilteredCollection() {
        Collection<Entry> entries = getEntries();
        Query singleQuery = new SingleQuery(new Boundary(
                new Coordinate(1, 2),
                new Coordinate(4, 2),
                new Coordinate(4, 5),
                new Coordinate(1, 5)
        ));

        Set<Entry> expected = new HashSet<>();
        expected.add(new MineEntry(getCoordinate(4, 3), 120));
        expected.add(new MineEntry(getCoordinate(2, 2), 250));
        expected.add(new Claim(getCoordinate(2, 5), 10000));
        expected.add(new Claim(getCoordinate(4, 4), 30000));
        expected.add(new Claim(getCoordinate(1, 4), 500));
        Collection<Entry> actual = service.process(entries, singleQuery);
        Assertions.assertTrue(expected.size() == actual.size());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldApplyGivenQueryWithOuterBoundaryAndReturnEmptyCollection() {
        Collection<Entry> entries = getEntries();
        Query singleQuery = new SingleQuery(new Boundary(
                getCoordinate(-5, -7),
                getCoordinate(-9, -7),
                getCoordinate(-9, -9),
                getCoordinate(-5, -9)
        ));
        Assertions.assertTrue(() -> service.process(entries, singleQuery).isEmpty());
    }

    @Test
    public void shouldApplyEmptyMultipleQueryAndReturnSameCollection() {
        Collection<Entry> entries = getEntries();
        Query multipleQuery = new MultipleQuery();
        Collection<Entry> expected = entries.stream().collect(Collectors.toSet());

        Collection<Entry> result = service.process(entries, multipleQuery);

        Collection<Entry> actual = result.stream().collect(Collectors.toSet());
        Assertions.assertTrue(expected.size() == actual.size());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldApplyGivenMultipleQueryAndReturnFilteredCollection() {
        Collection<Entry> entries = getEntries();
        Query multipleQuery = new MultipleQuery()
                .boundary(new Boundary(
                        new Coordinate(1, 2),
                        new Coordinate(4, 2),
                        new Coordinate(4, 5),
                        new Coordinate(1, 5)
                )).boundary(new Boundary(
                        new Coordinate(8, 2),
                        new Coordinate(9, 2),
                        new Coordinate(9, 3),
                        new Coordinate(8, 3)
                ));

        Set<Entry> expected = new HashSet<>();
        expected.add(new MineEntry(getCoordinate(4, 3), 120));
        expected.add(new MineEntry(getCoordinate(2, 2), 250));
        expected.add(new MineEntry(getCoordinate(9, 3), 90));
        expected.add(new Claim(getCoordinate(2, 5), 10000));
        expected.add(new Claim(getCoordinate(4, 4), 30000));
        expected.add(new Claim(getCoordinate(1, 4), 500));

        Collection<Entry> result = service.process(entries, multipleQuery);

        Set<Entry> actual = result.stream().collect(Collectors.toSet());
        Assertions.assertTrue(expected.size() == actual.size());
        Assertions.assertEquals(expected, actual);

        logger.info("**** Filtered data START *** \n\n");
        actual.forEach(e -> logger.info(e.toString()));
        logger.info("\n\n**** Filtered data END *** \n\n");
    }


    @Test
    public void shouldApplyGivenMultipleQueryWithBoundValueAndReturnFilteredCollection() {
        Collection<Entry> entries = getEntries();
        Query multipleQuery = new MultipleQuery()
                .boundary(new Boundary(
                        new Coordinate(1, 2),
                        new Coordinate(4, 2),
                        new Coordinate(4, 5),
                        new Coordinate(1, 5)
                )).boundary(new Boundary(
                        new Coordinate(8, 2),
                        new Coordinate(9, 2),
                        new Coordinate(9, 3),
                        new Coordinate(8, 3)
                )).boundValue(500);

        Set<Entry> expected = new HashSet<>();
        expected.add(new MineEntry(getCoordinate(4, 3), 120));
        expected.add(new MineEntry(getCoordinate(2, 2), 250));
        expected.add(new MineEntry(getCoordinate(9, 3), 90));
        expected.add(new Claim(getCoordinate(2, 5), 10000));
        expected.add(new Claim(getCoordinate(4, 4), 30000));

        Collection<Entry> result = service.process(entries, multipleQuery);

        Set<Entry> actual = result.stream().collect(Collectors.toSet());
        Assertions.assertTrue(expected.size() == actual.size());
        Assertions.assertEquals(expected, actual);

        logger.info("**** Filtered data START *** \n\n");
        actual.forEach(e -> logger.info(e.toString()));
        logger.info("\n\n**** Filtered data END *** \n\n");
    }
    private static Coordinate getCoordinate(int latitude, int longitude) {
        return new Coordinate(latitude, longitude);
    }

    private Collection<Entry> getEntries() {
        return Arrays.asList(
                new MineEntry(getCoordinate(4, 3), 120),
                new MineEntry(getCoordinate(7, 9), 80),
                new MineEntry(getCoordinate(9, 3), 90),
                new MineEntry(getCoordinate(8, 5), 50),
                new MineEntry(getCoordinate(2, 2), 250),
                new Claim(getCoordinate(2, 5), 10000),
                new Claim(getCoordinate(4, 4), 30000),
                new Claim(getCoordinate(1, 4), 500));

    }
}
