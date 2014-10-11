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


## License

Copyright 2014 Erik Swanson

The contents of this repository are distributed under the MIT Public
License. See LICENSE for more information.
