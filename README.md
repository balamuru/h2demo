# H2 server vs Embeedded mode
##Description
sample project to illustrate the difference in performance between h2 embedded mode and h2 server mode

## Switching between Server and Embedded mode
* uncomment the appropriate config line in resources/application.properties
* note that for server mode, H2 needs to be downloaded from http://www.h2database.com/html/download.html and executed via path/to/h2/bin/h2.sh (or h2.bat) 

## Results
* each run inserts 20 iterations x 100K employee objects  of type
admin.employee (
  id      BIGINT       NOT NULL AUTO_INCREMENT,
  name    VARCHAR(100) NOT NULL,
  address TEXT,
  data MEDIUMBLOB,
  PRIMARY KEY (id)
);

* no items are deleted between runs

* the test case cleans up the admin.employee table during initialization

```
embedded mode (msecs)
Run time : 27464
Run time : 7281
Run time : 6363
Run time : 6326
Run time : 5522
Run time : 6070
Run time : 5262
Run time : 6502
Run time : 6016
Run time : 7196
Run time : 15735 ==> spike in run time
Run time : 39973 ==> embedded mode slower than server mode from this run onwards
Run time : 44079
Run time : 46769
Run time : 50443
Run time : 53640
Run time : 59635
Run time : 61937
Run time : 64344
Run time : 71259

server mode (msecs)
Run time : 20872
Run time : 17319
Run time : 18081
Run time : 18205
Run time : 18596
Run time : 18089
Run time : 18453
Run time : 18386
Run time : 18330
Run time : 17259
Run time : 18826
Run time : 19717
Run time : 19122
Run time : 18152
Run time : 18016
Run time : 18818
Run time : 19414
Run time : 19207
Run time : 19022
Run time : 19446
```


