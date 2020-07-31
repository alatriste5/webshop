-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2020. Júl 31. 11:53
-- Kiszolgáló verziója: 10.4.13-MariaDB
-- PHP verzió: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `webshopdb`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `country` varchar(255) COLLATE latin2_hungarian_ci NOT NULL,
  `city` varchar(255) COLLATE latin2_hungarian_ci NOT NULL,
  `postcode` int(11) NOT NULL,
  `street` varchar(255) COLLATE latin2_hungarian_ci NOT NULL,
  `house` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin2 COLLATE=latin2_hungarian_ci;

--
-- A tábla adatainak kiíratása `address`
--

INSERT INTO `address` (`id`, `country`, `city`, `postcode`, `street`, `house`) VALUES
(1, 'Magyarország', 'Mezőkövesd', 3400, 'Dózsa György út', 1),
(2, 'Magyarország', 'Eger', 3300, 'Hadnagy utca', 4),
(3, 'Oroszország', 'Moskwa', 5, 'Jablaka street', 4),
(4, 'Ausztria', 'Linz', 88, 'Mozart street', 5);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE latin2_hungarian_ci NOT NULL,
  `price` int(11) NOT NULL,
  `details` text COLLATE latin2_hungarian_ci DEFAULT NULL,
  `sellerid` int(11) NOT NULL,
  `customerid` int(11) DEFAULT NULL,
  `soldat` timestamp NULL DEFAULT NULL,
  `valid` int(11) NOT NULL DEFAULT 0,
  `image` varchar(255) COLLATE latin2_hungarian_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin2 COLLATE=latin2_hungarian_ci;

--
-- A tábla adatainak kiíratása `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `details`, `sellerid`, `customerid`, `soldat`, `valid`, `image`) VALUES
(1, 'Dog', 21, 'Golden retriver', 1, NULL, NULL, 0, 'https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=1.00xw:0.669xh;0,0.190xh&resize=1200:*'),
(2, 'Cowboy hat', 33, 'White cowboy hat', 4, NULL, NULL, 1, 'https://www.bootbarn.com/on/demandware.static/-/Sites-master-product-catalog-shp/default/dw196fac08/images/919/2000234919_103_P1.JPG'),
(3, 'Bath duck', 2, 'Original bath toy', 3, 0, NULL, 1, 'https://www.bigw.com.au/medias/sys_master/images/images/h4e/h5c/11881557524510.jpg'),
(4, 'Volleyball', 25, 'Iconic Wilson volleyball', 3, 0, NULL, 1, 'https://images-na.ssl-images-amazon.com/images/I/91ghLcw5RyL._AC_SL1500_.jpg'),
(5, 'Screwdriver', 21, 'Stainless steel', 3, 4, '2020-07-31 06:47:39', 2, 'https://w0.pngwave.com/png/147/111/screwdriver-png-clip-art.png'),
(6, 'Brick', 2, 'Good to build a house', 4, 0, NULL, 0, 'https://mobileimages.lowes.com/product/converted/693092/693092000005xl.jpg'),
(7, 'Mosquito Spray', 12, 'You will definitely need it at riverside', 1, 0, NULL, 1, 'https://www.lessmosquito.com/images/products/small/IC209SPRAYN-200226092658-1.png'),
(8, 'American football', 18, 'Ball of the best teamsport', 1, 6, '2020-07-31 06:47:54', 2, 'https://images-na.ssl-images-amazon.com/images/I/91FzIp4UdjL._AC_SX522_.jpg'),
(9, 'Mouse', 7, 'Computer mouse', 2, 0, NULL, 1, 'https://assets.logitech.com/assets/64365/2/wireless-mouse-m185.png'),
(10, 'Bicycle', 49, 'General bicycle', 2, 0, NULL, 0, 'https://cdn.shopify.com/s/files/1/1772/1703/t/16/assets/cowboy-3-absolute-black_w_6.png?v=15537051773153245689'),
(11, 'Dragon balls', 99, 'Crystal dragon balls', 1, 0, NULL, 1, 'https://images-na.ssl-images-amazon.com/images/I/61G3zsBeDSL._AC_SL1005_.jpg');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(25) COLLATE latin2_hungarian_ci NOT NULL,
  `name` varchar(50) COLLATE latin2_hungarian_ci NOT NULL,
  `email` varchar(50) COLLATE latin2_hungarian_ci NOT NULL,
  `password` varchar(50) COLLATE latin2_hungarian_ci NOT NULL,
  `addressid` int(11) NOT NULL,
  `role` varchar(10) COLLATE latin2_hungarian_ci NOT NULL,
  `createdat` timestamp NOT NULL DEFAULT current_timestamp(),
  `valid` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin2 COLLATE=latin2_hungarian_ci;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `username`, `name`, `email`, `password`, `addressid`, `role`, `createdat`, `valid`) VALUES
(1, 'admin', 'Admin Janos', 'admin@admin.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 1, 'Admin', '2020-07-31 09:45:18', 0),
(2, 'balazs799', 'Tóth Balázs', 'balazs799@gmail.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 2, 'Admin', '2020-07-31 09:45:50', 0),
(3, 'Teszt10', 'Temp Bela', 'asdfa@sdfa.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 3, 'User', '2020-07-31 09:46:12', 0),
(4, 'fetomy', 'Tom Tom', 'tom@tomtom.com', 'mFtwTt+oWtkG8zkf+YW1Mw==', 4, 'User', '2020-07-31 09:46:44', 0);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `address`
--
ALTER TABLE `address`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sellerid` (`sellerid`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`sellerid`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
