-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2020. Júl 22. 09:19
-- Kiszolgáló verziója: 10.1.36-MariaDB
-- PHP verzió: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `webshop`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `address`
--

CREATE TABLE `address` (
  `id` int(11) NOT NULL,
  `country` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `postcode` int(11) NOT NULL,
  `street` varchar(255) NOT NULL,
  `house` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `address`
--

INSERT INTO `address` (`id`, `country`, `city`, `postcode`, `street`, `house`) VALUES
(1, 'Hungary', 'Mezokovesd', 3400, 'Dozsa Gyorgy ut', 101),
(2, 'Hungary', 'Eger', 3300, 'Hadnagy utca', 31),
(3, 'Hungary', 'Eger', 3300, 'Kossuth utca', 1),
(4, 'Hungary', 'Budapest', 3300, 'Vágány utca', 32),
(5, 'Hungary', 'Eger', 3400, 'Hadnagy út', 4),
(6, 'Finnland', 'Helsinky', 1, 'Stokholm street', 81),
(7, 'Austria', 'Linz', 88, 'Mozart street', 100),
(8, 'Hungary', 'Mezokovesd', 3400, 'Fecske utca', 21);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `details` text,
  `sellerid` int(11) NOT NULL,
  `customerid` int(11) DEFAULT NULL,
  `valid` int(11) NOT NULL DEFAULT '0',
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `details`, `sellerid`, `customerid`, `valid`, `image`) VALUES
(4, 'Gardner Minshew II', 9750, 'a legnagyobb király', 5, 0, 1, 'https://image.cnbcfm.com/api/v1/image/106180710-1571080140445gettyimages-1175825543.jpeg?v=1571083730'),
(6, 'Mill', 430, 'Wheat grinding', 1, 0, 1, 'https://keptar.oszk.hu/034200/034263/34052381_nagykep.jpg'),
(8, 'Brick', 1, 'This brick can be use for build a house!', 5, 0, 0, 'https://images.homedepot-static.com/productImages/21bd11f8-81e9-4ea6-a9c8-cba1ed8119e7/svn/bricks-red0126mco-64_300.jpg'),
(10, 'Matrjoska', 150, 'Traditional Russian toy.', 37, 0, 1, 'https://www.urbanlegends.hu/wp-content/uploads/matrjoska.jpg'),
(11, 'Asian Sport Shoes', 33, 'Cool shoes.', 6, 0, 1, 'https://images-na.ssl-images-amazon.com/images/I/61utX8kBDlL._UL1100_.jpg'),
(13, 'Nice Stickers for everybody', 10, 'Upgrade your books and bags! Make your things looks better!', 6, 0, 1, 'https://images-na.ssl-images-amazon.com/images/I/61uqlt-XQiL._AC_SY355_.jpg'),
(14, 'Gucci backpack', 9500, 'Famous leather backpack for women', 5, 0, 1, 'https://cdn-images.farfetch-contents.com/13/43/39/84/13433984_21892108_600.jpg'),
(17, 'Belt', 23, 'Levis belt', 37, 0, 1, 'https://images-na.ssl-images-amazon.com/images/I/71%2Bzu2zY4rL._UL1500_.jpg'),
(18, 'Oakley sunglasses', 75, 'Best sunglasses ever!', 37, 0, 1, 'https://img.edel-optics.hu/eoProductsGlassesSunglasses/640/nobg/Oakley-OO9102-9102C1.jpg'),
(19, 'Fox cap', 30, 'Black Fox cap', 5, 0, 1, 'https://images-na.ssl-images-amazon.com/images/I/91pEJ5np00L._AC_UX679_.jpg');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `addressid` int(11) NOT NULL,
  `role` varchar(10) NOT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `username`, `name`, `email`, `password`, `addressid`, `role`, `createdat`) VALUES
(1, 'Pukina', 'Tom Tom', 'tom@gmail.com', '4v0tWVTne472W5upHKG8nA==', 3, 'User', '2020-07-03 14:30:33'),
(2, 'hapi', 'Hapa László', 'hapi@mittudomen.com', 'D4W6+gfrj2vWVM1JIu+5ew==', 4, 'User', '2020-07-03 14:30:33'),
(3, 'Hendo', 'Jordan Henderson', 'henderson@liverpool.com', 'N8YC6wtlukjWVJY3SlKV5g==', 3, 'User', '2020-07-03 14:30:33'),
(5, 'balazs79', 'Tóth Balázs', 'balazs799@gmail.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 2, 'User', '2020-07-03 16:53:38'),
(6, 'admin', 'Admin', 'admin@admin.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 1, 'Admin', '2020-07-13 18:15:14'),
(37, 'fetomy', 'sdaf fdsdsa', 'dsfsad@ddd.hu', 'Yckg2sRG1XTa6nHPdIwdEg==', 6, 'User', '2020-07-17 13:55:33'),
(43, 'Teszt10', 'Teszt Tíz', 'tiz@teszt.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 7, 'User', '2020-07-21 14:28:40'),
(44, 'Nati', 'Szekely-Toth Natali', 'nati@mail.com', 'Yckg2sRG1XTa6nHPdIwdEg==', 8, 'User', '2020-07-21 15:37:51');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

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
