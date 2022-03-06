# log4j2-clogger-pattern

A Log4J2 plugin that provides a `%clogger` pattern inspired by [Logback's
`%logger` pattern][logback-logger].

"clogger" is short for "collapsed logger", *not* a [clog][clog-wiki]
aficionado.

## Usage

The plugin should be automatically loaded by being present on the classpath.

The pattern takes a single argument which specifies the target width for
the logger name. The logger name may still exceed this target since the
class name is never abbreviated (i.e. only package names are abbreviated).

Pattern        | Logger                     | Result
---------------|----------------------------|---------------------------
`%clogger`     | mainPackage.sub.sample.Bar | mainPackage.sub.sample.Bar
`%clogger{0}`  | mainPackage.sub.sample.Bar | Bar
`%clogger{5}`  | mainPackage.sub.sample.Bar | m.s.s.Bar
`%clogger{10}` | mainPackage.sub.sample.Bar | m.s.s.Bar
`%clogger{15}` | mainPackage.sub.sample.Bar | m.s.sample.Bar
`%clogger{16}` | mainPackage.sub.sample.Bar | m.sub.sample.Bar
`%clogger{26}` | mainPackage.sub.sample.Bar | mainPackage.sub.sample.Bar

In addition to `%clogger`, this pattern is also aliased to `%collapsedlogger`
(for clarity) and `%lo` (for brevity). `%lo` was chosen because this is used
in Logback but is not currently used in Log4J2.

## Motivation

[Log4J2's `%logger`][log4j2-logger] is configured in terms of packages: users
specify how many  package components to abbreviate/remove. Unfortunately, not
all libraries have  the same depth of packages. This can force customers to
sacrifice either brevity or clarity.

On the contrary, [Logback's `%logger`][logback-logger] is configured in terms
of characters. Packages are abbreviated until either the logger name is short
enough or only the class name remains. This approach leaves as much context as
possible and works reasonably well regardless of how deep the package hierarchy
is.

[log4j2-logger]: https://logging.apache.org/log4j/2.x/manual/layouts.html#Patterns
[logback-logger]: https://logback.qos.ch/manual/layouts.html#logger
[clog-wiki]: https://en.wikipedia.org/wiki/Clog
