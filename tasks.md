### 1. Zwykłe zliczanie słów bez użycia map-reduce

- zapoznać się z klasami BookInput, InputStreamLineIterator i LineTokenizer
- uzupełnić brakującą implementację w klasie NaiveWordCount
- zweryfikować rozwiązanie za pomocą testu w klasie NaiveWordCountTest
- uruchomić benchmark w klasie NaiveWordCountBenchmark i sprawdzić wyniki

### 2. Profiler i flame graph

- uruchomić metodę main w klasie NaiveWordCount z użyciem profilera
- na podstawie wykresu flame graph zastanowić się gdzie można zoptymalizować istniejący kod ?

### 3. Sekwencyjne map-reduce
 
- zapoznać się z interfejsami MapReduce, Mapper, Reducer 
- zapoznać się z klasą SequentialMapReduce będącą sekwencyjną implementacją interfejsu MapReduce
- zapoznać się z klasą WordCountMapReduce zliczającą słowa za pomocą wybranej implementacji MapReduce
- uzupełnić implementację klas WordCountMapper i WordCountReducer
- zweryfikować poprawność działania za pomocą testu w klasie SequentialWordCountTest
- uruchomić benchmark w klasie SequentialWordCountBenchmark i porównać wyniki z poprzednimi

### 4. Równoległe map-reduce

- uzupełnić implementację w klasie ParallelMapReduce
- implementacja ma w założeniu spowodować równoległe wykonywanie fazy map dla każdej linii na wejściu
- faza reduce jest wykonywana bez zmian (ale dopiero po tym jak zakończą się wszystkie zadania z fazy map)
- podpowiedź: użyć ExecutorService i CompletionService 
- zweryfikować rozwiązanie za pomocą testu w klasie ParallelWordCountTest
- uruchomić benchmark w klasie ParallelWordCountBenchmark i porównać wyniki z wersją sekwencyjną

### 5. Równoległe map-reduce z podziałem na paczki (batching)

- uzupełnić implementację w klasie BatchingParallelMapReduce
- w tym przypadku mapowanie powinno odbywać się dla n linii i każdy mapper powinien dysponować swoją mapą z wynikami
- wartość n będzie przekazywana z zewnątrz (batchSize) 
- takie podejście redukuje contention
- następnie można połączyć takie mapy w jedną i po tym wykonać zwykłe reduce
- zweryfikować rozwiązanie za pomocą testu w klasie BatchingParallelWordCountTest
- uruchomić benchmark w klasie BatchingParallelWordCountBenchmark i porównać wyniki z wersją sekwencyjną
