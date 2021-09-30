# ReCapProject
 ## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Requirements](#requirements)


## General info
### ReCap Project - Backend of a Rent a Car Application :red_car:

An application that allows people to rent a car. 
	
## Technologies
Technologies that used in this project are:
* Java
* SQL
* PostGreSQL
* Swagger
* Spring Boot

<img align="left" alt="Java" width="26px" src="https://classes.engineering.wustl.edu/cse231/core/images/2/26/Java.png" />
<img align="left" alt="SQL" width="26px" src="https://uploads.toptal.io/blog/category/logo/60/sql.png" />
<img align="left" alt="PostGreSQL" width="26px" src="https://raw.githubusercontent.com/github/explore/80688e429a7d4ef2fca1e82350fe8e3517d3494d/topics/postgresql/postgresql.png" />
<img align="left" alt="Swagger" width="26px" src="https://www.form.io/sites/default/files/2018-08/swagger-300.jpg" />
<img align="left" alt="SpringBoot" width="26px" src="https://www.instana.com/media/spring_boot_logo.png" />
<img align="left" alt="GitHub" width="26px" src="https://raw.githubusercontent.com/github/explore/78df643247d429f6cc873026c0622819ad797942/topics/github/github.png" />

<br/>
<br/>

## Requirements

1- Yepyeni bir proje oluşturunuz. Adı ReCapProject olacak. (Tekrar ve geliştirme projesi)
Entities, DataAccess, Business oluşturunuz.
Bir araba nesnesi oluşturunuz. "Car"
Brand ve Color nesnelerini oluşturunuz.(BrandId,BrandName…
Özellik olarak : Id, BrandId, ColorId, ModelYear, DailyPrice, Description alanlarını ekleyiniz. (Brand = Marka)

2- Car, Brand, Color sınıflarınız için tüm CRUD operasyonlarını hazır hale getiriniz.
Console'da Tüm CRUD operasyonlarınızı Car, Brand, Model nesneleriniz için test ediniz. GetAll, GetById, Insert, Update, Delete.
Arabaları şu bilgiler olacak şekilde listeleyiniz. CarName, BrandName, ColorName, DailyPrice. (İpucu : Dto oluşturup 3 tabloya join yazınız)

3- Core katmanında Results yapılandırması yapınız.

4- Kullanıcılar tablosu oluşturunuz. Users-->Id,FirstName,LastName,Email,Password
Müşteriler tablosu oluşturunuz. Customers-->UserId,CompanyName
Kullanıcılar ve müşteriler ilişkilidir.
Arabanın kiralanma bilgisini tutan tablo oluşturunuz. Rentals-->Id, CarId, CustomerId, RentDate(Kiralama Tarihi), ReturnDate(Teslim Tarihi). Araba teslim edilmemişse ReturnDate null'dır.
Projenizde bu entity'leri oluşturunuz.
CRUD operasyonlarını yazınız.
Yeni müşteriler ekleyiniz.
Arabayı kiralama imkanını kodlayınız. Rental-->Add
Arabanın kiralanabilmesi için arabanın teslim edilmesi gerekmektedir.

5- WebAPI katmanını kurunuz.
Business katmanındaki tüm servislerin Api karşılığını yazınız.
Swagger’da test ediniz.

6- FluentValidation desteği ekleyiniz.

7- CarImages (Araba Resimleri) tablosu oluşturunuz. (Id,CarId,ImagePath,Date) Bir arabanın birden fazla resmi olabilir.
Api üzerinden arabaya resim ekleyecek sistemi yazınız.
Resimler projeniz içerisinde bir klasörde tutulacaktır. Resimler yüklendiği isimle değil, kendi vereceğiniz GUID ile dosyalanacaktır.
Resim silme, güncelleme yetenekleri ekleyiniz.
Bir arabanın en fazla 5 resmi olabilir.
Resmin eklendiği tarih sistem tarafından atanacaktır.
Bir arabaya ait resimleri listeleme imkanı oluşturunuz. (Liste)
Eğer bir arabaya ait resim yoksa, default bir resim gösteriniz. Bu resim şirket logonuz olabilir. (Tek elemanlı liste)

8- Brand listesinde herhangi bir marka seçildiğinde, o markaya ait arabaları listeleyiniz.
Color listesinde herhangi bir renk seçildiğinde, o renge ait arabaları listeleyiniz.
Car listesinde bir arabaya tıklandığında o arabaya ait detay sayfası oluşturunuz. Bu sayfada bu araca ait resimleri de gösteriniz.

9- Login/Register yetenekleri getiriniz.
Kiralama esnasında müşterinin findeks puanını sorgulayacak sahte servis ekleyiniz.
Findeks puan aralığı 0-1900 arasındadır.
Araçların kiralanabilmesi için her aracın ayrı ayrı minimum findeks puanı olmalıdır. Bu puanı olmayan müşteriler araç kiralayamaz.
Eğer giriş yapılmamışsa nav'da bu butonlar olsun. Giriş yapılmışsa bu butonlar yerine müşteri adı ex: "Engin Demiroğ" yazsın.
Kullanıcı adını yazdığınız kısım açılır kutu olmalı.
Kullanıcı bilgilerini görüp güncelleyebilmelidir.
Kredi kartıyla ödeme alındığında kullanıcıya kredi kartını kaydedelim mi? Sorusu yöneltiniz. Kaydetmek isteyen müşteriye sonraki ödemede kayıtlı kredi kartını gösteriniz.

10- Markalar tekrar edemez. (Brand-add)
11- (Düzeltme) Kredi kartı formatı control edilmeli. Regex
12- Arabalar bakıma gönderilebilmelidir.
Kirada olan bir araba bakıma gönderilemez.
Bakımda olan bir araba kiralanamaz.
Bakımda olan araba tüm listelerde listelenmemelidir.
13- Şirketimiz büyüdü. Kurumsal müşteriler araba kiralayabilmelidir. (Kurumsal Müşteri – vergiNo, Şirket ismi,email….
	Kiralama kuralları bireysel müşteri ile aynıdır.
14- Tüm kiralamalar sonucunda fatura kesilmelidir. (Fatura No, Oluşturma Tarihi, Kiralama tarihleri, Toplam kiralama gün sayısı, kiralama tutarı)
	Belirli tarih aralığında tüm faturalar listelenebilmelidir.
           Müşteriye ait faturalar listelenebilmelidir.
15- Farklı illerde şubeler açmaya karar verdik. Arabalara şehir bilgisi eklenmelidir.
Şehir bilgisine göre arabalar listelenebilmelidir.
Kiralamada alış şehri – dönüş şehri bilgisi eklenmelidir.
Araba teslim edildiğinde, dönüş şehri farklıysa, araba dönüş ili portföyüne girmelidir.

16- Arabalara güncel kilometre bilgisi eklenmelidir.
Kiralamalarda başlıngıç kilometresi girilmelidir.
Kiralama Dönüşlerinde dönüş kilometresi bilgisi girilmelidir. Bu bilgi arabada da güncellenmelidir.
17- Arabaya ait hasarlar girilebilmelidir. (Id,CarId,HasarBilgisi)

