-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 14, 2020 at 12:10 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `java_diploma`
--

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
  `id` int(11) UNSIGNED NOT NULL,
  `title` varchar(150) NOT NULL,
  `intro` varchar(250) NOT NULL,
  `text` text NOT NULL,
  `views` int(3) UNSIGNED NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `articles`
--

INSERT INTO `articles` (`id`, `title`, `intro`, `text`, `views`) VALUES
(1, 'Google рулит', 'Текст для анонса', 'Полный текст статьи про Google ', 8),
(2, 'Apple рулит', 'Текст анонс еще одной статьи', 'Полный текст статьи про Apple', 88),
(3, 'Facebook рулит', 'Анонс статьи про Facebook ', 'Полный текст статьи про Facebook. \nПолный текст статьи про Facebook. \nПолный текст статьи про Facebook. \nПолный текст статьи про Facebook.\n----- \nПолный текст статьи про Facebook. \nПолный текст статьи про Facebook. \nПолный текст статьи про Facebook. \n/////////////////////\nПолный текст статьи про Facebook. \nПолный текст статьи про Facebook. \nПолный текст статьи про Facebook. ', 0),
(4, 'Yandex рулит', 'Анонс статьи про Yandex ', 'Полный текст статьи про Yandex \nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \n////////\nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \nПолный текст статьи про Yandex \n--------\nПолный текст статьи про Yandex \n+++++\nПолный текст статьи про Yandex ', 0);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(3),
(3);

-- --------------------------------------------------------

--
-- Table structure for table `link_shorter`
--

CREATE TABLE `link_shorter` (
  `id` int(11) UNSIGNED NOT NULL,
  `link` varchar(255) NOT NULL,
  `short_link` varchar(15) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `link_shorter`
--

INSERT INTO `link_shorter` (`id`, `link`, `short_link`) VALUES
(1, 'https://itproger.com/course/android-firebase', 'ut'),
(2, 'https://itproger.com/course/android-firebase', 'utt'),
(3, 'https://itproger.com/course/android-firebase', 'itPR_android'),
(4, 'https://www.songsterr.com/a/wsa/cradle-of-filth-dirge-inferno-tab-s28612t1', 'sn'),
(5, 'https://programmerfriend.com/best-java-books-2020/#introduction-to-java-programming-and-data-structures-by-y-daniel-liang', 'jbb'),
(6, 'https://www.youtube.com/watch?v=A6RYgyuw0aY', 'hate'),
(7, 'https://www.youtube.com/watch?v=QMMJpFvGMlU&list=PLnUNgWk4CcyZiH4ZdkUR7AktqB-iqfdg6', 'slip2019');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` bigint(20) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `text`, `title`, `user_id`) VALUES
(2, 'Новый отзыв', 'Отзыв', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `password`, `username`) VALUES
(1, 'konan88@inbox.ru', b'1', '123', 'ss');

-- --------------------------------------------------------

--
-- Table structure for table `users_fx`
--

CREATE TABLE `users_fx` (
  `id` int(11) UNSIGNED NOT NULL,
  `login` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_fx`
--

INSERT INTO `users_fx` (`id`, `login`, `email`, `password`) VALUES
(2, 'serg', 's@m.m', '01cfcd4f6b8770febfb40cb906715822');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `roles`) VALUES
(1, 'ADMIN');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `link_shorter`
--
ALTER TABLE `link_shorter`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6cpw2nlklblpvc7hyt7ko6v3e` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_fx`
--
ALTER TABLE `users_fx`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKj345gk1bovqvfame88rcx7yyx` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `articles`
--
ALTER TABLE `articles`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `link_shorter`
--
ALTER TABLE `link_shorter`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `users_fx`
--
ALTER TABLE `users_fx`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `FK6cpw2nlklblpvc7hyt7ko6v3e` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
