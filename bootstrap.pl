#!/usr/bin/perl

use warnings;
use strict;

my $basepath = "/home/fluff/git/adventofcode2019/src/se/fluff/aoc2020";

my $day = $ARGV[0];
my $year = `date +"%Y"`;
chomp($year);

if(length($day) < 2) {
    print "Must supply day as argument";
    exit;
}

mkdir $basepath . qq!/tests/$day!;
mkdir $basepath . qq!/tests/$day/a!;
mkdir $basepath . qq!/tests/$day/b!;

if(!-e $basepath . qq!/days/Day$day.java!) {
    open(FH, '>', $basepath . qq!/days/Day$day.java!);
    print FH <<"END";
package se.fluff.aoc$year.days;

import se.fluff.aoc.AocDay;
import java.util.Scanner;

/**
 * Created by Fluff on $year-12-$day.
 */
public class Day$day extends AocDay {

    public Day$day() {

    }

    \@Override
    public String a(Scanner in) {
        return null;
    }

    \@Override
    public String b(Scanner in) {
        return null;
    }
}
END
}

open(FH, '>', $basepath . qq!/data/$day.in!);
print "Enter input from AoC, thereafter enter ! on a standalone line:\n";
while(my $in = <STDIN>) {
    last if($in =~ /^\!/);
    print FH $in;
}
close(FH);

print "\nHow many test cases for puzzle A: ";
my $testcasecount = <STDIN>;
chomp($testcasecount);

for(my $i = 1; $i <= $testcasecount; $i++) {
    system(qq!touch $basepath/tests/$day/a/$i.in!);
    system(qq!touch $basepath/tests/$day/a/$i.out!);
}

print "\nHow many test cases for puzzle B: ";
$testcasecount = <STDIN>;
chomp($testcasecount);

for(my $i = 1; $i <= $testcasecount; $i++) {
    system(qq!touch $basepath/tests/$day/b/$i.in!);
    system(qq!touch $basepath/tests/$day/b/$i.out!);
}

