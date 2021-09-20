# JPlotLib library

Main goal of the library is to provide simple API for data preparation
to plot input.

## Input

There are three ways to provide data feed:

- path to csv file
- two-dimensional array of String values
- entity list

Additionally, each API entry has its requirements of additional parameters 
needed for transformations and conversions.

## Features

Library gives the following capabilities:

- Choosing argument and value of the plot by name, set type of arguments 
and values
- Grouping values by arguments
- Choosing function for grouping values: MAX, MIN, SUM, AVG
- Validation of inputs, type conversion and aggregation type choice

## API entry

User of the library should use PlotDataBuilder as an entry. This class should be the used as the **only** 
way to assemble the PlotData object, as this way it's assured that input data, info and aggregation type
are valid and suitable to the transformation user want to make.

## Licence

[MIT](https://choosealicense.com/licenses/mit/)