@echo off
REM Run all datasets and produce JSON+CSV summaries per dataset.
set CP=.;lib\*;src\main\java
java -cp "%CP%" com.mst.Main out_small.json summary_small.csv src\main\resources\assign_3_input_small.json
java -cp "%CP%" com.mst.Main out_medium.json summary_medium.csv src\main\resources\assign_3_input_medium.json
java -cp "%CP%" com.mst.Main out_large1.json summary_large1.csv src\main\resources\assign_3_input_large_1.json
java -cp "%CP%" com.mst.Main out_large2.json summary_large2.csv src\main\resources\assign_3_input_large_2.json
java -cp "%CP%" com.mst.Main out_extra_large.json summary_extra_large.csv src\main\resources\assign_3_input_extra_large.json
echo Done.
