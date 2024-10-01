 Feature: login testleri

   @regression
   Scenario: kullanici basarili bir sekilde login olabilmelidir
        Given kullanici login sayfasina gider
        When kullanici dogru bilgileri girer
        Then kullanici anasayfaya yonlendirilir
