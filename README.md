Music Abstractions
==================

Music, a lot like programming, can be built as a series of
abstractions from simple underlying components. This repository is the
source code for a live coding presentation given to [Pittsburgh Code &
Supply](http://www.codeandsupply.co).

This talk focused on building from a very low level of abstraction,
pure tones in the form of simple sine waves, into instruments that
could play notes, up to sequences of notes, and finally a small
composition of sequences.

The file
[`src/music_abstractions/talk.clj`](https://github.com/Wilduck/overtone-music-abstractions-presentation/blob/master/src/music_abstractions/talk.clj)
is the source code typed in during the course of the talk, including
comments with demo examples. The file
[`src/music_abstractions/composition.clj`](https://github.com/Wilduck/overtone-music-abstractions-presentation/blob/master/src/music_abstractions/composition.clj) includes the instruments
definitions, sequencer function definitions, patterns, and composition
in a clean progression to a simple generative piece of music.

## The Tools

To make use of the code in this talk, you will need
[Clojure](http://clojure.org/) (likely with
[Leiningen](http://leiningen.org/)), And the
[Overtone](http://overtone.github.io/) libary.

The presentation was given using
[Emacs](http://www.gnu.org/software/emacs/), using
[Cider](https://github.com/clojure-emacs/cider) to connect to a
networked Clojure REPL ([nREPL](https://github.com/clojure/tools.nrepl)).

## Getting Started with Overtone

For someone interested in getting started with Overtone, the
[introductory documentation](https://github.com/overtone/overtone/wiki)
is fairly good and is definitely the right place to start. However, it
is not at all comprehensive. For someone interested in going beyond
the introductory documentation, but unsure how to proceede, I have two
suggestions.

1. Within the Overtone source,
   [available on github](https://github.com/overtone/overtone/), are a
   large number of
   [examples](https://github.com/overtone/overtone/tree/master/src/overtone/examples). These
   examples are not referenced in the introductory documentation, but
   cover a much greater breadth of the functionality available through
   Overtone.

2. At the REPL, Overtone provides a help function, `odoc` which will
   print off documentation for a given command. For example:

        => (odoc sin-osc)

   will print off information about the sine wave oscillator ugen.

Simply typing in some of the examples linked above, and calling the
`odoc` function on anything I did not immediately understand, helped
me to quickly grasp a significant portion of what was possible with
Overtone.


## License

Copyright 2014 Erik Swanson

The contents of this repository are distributed under the MIT Public
License. See LICENSE for more information.
