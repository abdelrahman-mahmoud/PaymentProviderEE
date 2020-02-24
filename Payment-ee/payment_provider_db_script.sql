-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 23, 2020 at 09:53 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `payment_provider_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `currency`
--

CREATE TABLE `currency` (
  `id` int(11) NOT NULL,
  `currency_code` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `currency`
--

INSERT INTO `currency` (`id`, `currency_code`) VALUES
(1, 'EUR'),
(3, 'GBP'),
(2, 'USD');

-- --------------------------------------------------------

--
-- Table structure for table `order_payment`
--

CREATE TABLE `order_payment` (
  `id` int(11) NOT NULL,
  `order_id` varchar(250) NOT NULL,
  `client_id` varchar(250) NOT NULL,
  `order_status_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `currency_id` int(11) NOT NULL,
  `payment_method_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `token` varchar(250) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_on` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_payment`
--


-- --------------------------------------------------------

--
-- Table structure for table `order_status`
--

CREATE TABLE `order_status` (
  `id` int(11) NOT NULL,
  `order_status_code` varchar(5) NOT NULL,
  `order_status_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_status`
--

INSERT INTO `order_status` (`id`, `order_status_code`, `order_status_name`) VALUES
(1, 'REG', 'ORDER-REGISTERED'),
(2, 'AUTH', 'ORDER-AUTHORISED'),
(3, 'CAP', 'ORDER-CAPTURED'),
(4, 'REV', 'ORDER-REVERSED');

-- --------------------------------------------------------

--
-- Table structure for table `payment_method`
--

CREATE TABLE `payment_method` (
  `id` int(11) NOT NULL,
  `payment_method_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment_method`
--

INSERT INTO `payment_method` (`id`, `payment_method_name`) VALUES
(1, 'Card'),
(3, 'Cash'),
(2, 'Invoice');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `id` int(11) NOT NULL,
  `status_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`id`, `status_name`) VALUES
(2, 'ERROR'),
(1, 'SUCCESS');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `order_payment_id` int(11) NOT NULL,
  `transaction_type_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--



-- --------------------------------------------------------

--
-- Table structure for table `transaction_type`
--

CREATE TABLE `transaction_type` (
  `id` int(11) NOT NULL,
  `tran_type_code` varchar(5) NOT NULL,
  `tran_type_desc` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction_type`
--

INSERT INTO `transaction_type` (`id`, `tran_type_code`, `tran_type_desc`) VALUES
(1, 'REG', 'Register'),
(2, 'AUTH', 'Authorise'),
(3, 'CAP', 'Capture'),
(4, 'REV', 'Reverse');

-- --------------------------------------------------------

--
-- Table structure for table `transaction_workflow_setup`
--

CREATE TABLE `transaction_workflow_setup` (
  `id` int(11) NOT NULL,
  `current_step` int(11) NOT NULL,
  `next_step` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction_workflow_setup`
--

INSERT INTO `transaction_workflow_setup` (`id`, `current_step`, `next_step`) VALUES
(1, 1, 2),
(2, 1, 4),
(3, 2, 3),
(4, 2, 4),
(5, 3, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `currency`
--
ALTER TABLE `currency`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `currency` (`currency_code`),
  ADD UNIQUE KEY `currency_2` (`currency_code`);

--
-- Indexes for table `order_payment`
--
ALTER TABLE `order_payment`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_composite_key_client_order_id` (`client_id`,`order_id`),
  ADD KEY `order_status_id` (`order_status_id`),
  ADD KEY `currency_id` (`currency_id`),
  ADD KEY `payment_method_id` (`payment_method_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `o_id` (`order_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `order_status`
--
ALTER TABLE `order_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment_method`
--
ALTER TABLE `payment_method`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `payment_method` (`payment_method_name`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `payment_status` (`status_name`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_payment_id`),
  ADD KEY `order_id_2` (`order_payment_id`),
  ADD KEY `transaction_type_id` (`transaction_type_id`),
  ADD KEY `status_id` (`status_id`);

--
-- Indexes for table `transaction_type`
--
ALTER TABLE `transaction_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tran_type_code` (`tran_type_code`);

--
-- Indexes for table `transaction_workflow_setup`
--
ALTER TABLE `transaction_workflow_setup`
  ADD PRIMARY KEY (`id`),
  ADD KEY `current_step` (`current_step`),
  ADD KEY `next_step` (`next_step`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `currency`
--
ALTER TABLE `currency`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `order_payment`
--
ALTER TABLE `order_payment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `order_status`
--
ALTER TABLE `order_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `payment_method`
--
ALTER TABLE `payment_method`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `transaction_type`
--
ALTER TABLE `transaction_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `transaction_workflow_setup`
--
ALTER TABLE `transaction_workflow_setup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_payment`
--
ALTER TABLE `order_payment`
  ADD CONSTRAINT `order_payment_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `order_payment_ibfk_2` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
  ADD CONSTRAINT `order_payment_ibfk_3` FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
  ADD CONSTRAINT `order_payment_ibfk_4` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`transaction_type_id`) REFERENCES `transaction_type` (`id`),
  ADD CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`order_payment_id`) REFERENCES `order_payment` (`id`);

--
-- Constraints for table `transaction_workflow_setup`
--
ALTER TABLE `transaction_workflow_setup`
  ADD CONSTRAINT `transaction_workflow_setup_ibfk_1` FOREIGN KEY (`current_step`) REFERENCES `order_status` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
