package brickset;
import repository.Repository;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }



    //megnézzük hogy egy adott string benne van e a LegoSetek neveiben
    public boolean NamesContainString(String _stuff) {
        return getAll().stream().anyMatch(legoSet -> legoSet.getName().contains(_stuff));
    }


    // A Tageket FlatMappoljuk és mindegyiket kiiratjuk a konzolra
    public void PrintFlatMapTags()
    {
        getAll().stream().filter(legoSet -> legoSet.getTags() != null).flatMap(legoSet -> legoSet.getTags().stream())
                .forEach(System.out::println);
    }

    // összes LegoSet Pieces értékeit összeadjuk
    public Integer ReduceAllPieces()
    {
        return getAll().stream().map(LegoSet::getPieces).reduce(0,Integer::sum);
    }

    // Hozzárendeljük a Pieces értékeket a LegoSet nevekhez, és visszadunk egy Mapot
    public Map<String,Integer> CollectNamesToPieces()
    {
        return getAll().stream().distinct().collect(Collectors.toMap(LegoSet::getName,LegoSet::getPieces,(r1, r2) -> r1));
    }


    // Összegyűjtük az azonos témájú LegoSeteket és összeszámoljuk őket
    public Map<String, Long> GroupAndCountLegoSetsByTheme()
    {
        return getAll().stream().collect(Collectors.groupingBy(LegoSet::getTheme,Collectors.counting()));
    }



    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        System.out.println(repository.NamesContainString("Asd"));
        repository.PrintFlatMapTags();
        System.out.println(repository.ReduceAllPieces());
        System.out.println(repository.CollectNamesToPieces());
        System.out.print(repository.GroupAndCountLegoSetsByTheme());
    }

}
