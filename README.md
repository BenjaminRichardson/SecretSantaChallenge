# SecretSantaChallenge
I did this as a coding challenge as part of an interview. There were two problems with different requirements. 
The question was themed around a secret santa draw for rockers, if you're wondering why the word rocker appears so much.

The first and more simple problem: Pair givers and receivers, avoid repeats from last year and avoid reciprocal pairings (A->B and B->A is not acceptable)

The second, more complex problem: Pair givers and receevers, avoid repeats from the last 4 years, avoid reciprocal pairings.

For the first questions I decided to go with a less time efficent but more space efficient algorithm using a circularly linked list. 

For the second question I generalized the problem to a matching problem and wrote an implementation of Edmonds-Karp to create the pairs.
