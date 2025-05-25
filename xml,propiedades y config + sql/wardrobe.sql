SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_agathion_list`
--

CREATE TABLE `character_dressme_agathion_list` (
  `charId` int NOT NULL,
  `agathionId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_armor_list`
--

CREATE TABLE `character_dressme_armor_list` (
  `charId` int NOT NULL,
  `dressId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_cloak_list`
--

CREATE TABLE `character_dressme_cloak_list` (
  `charId` int NOT NULL,
  `cloakDressId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_enchant_list`
--

CREATE TABLE `character_dressme_enchant_list` (
  `charId` int NOT NULL,
  `enchantId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_hat_list`
--

CREATE TABLE `character_dressme_hat_list` (
  `charId` int NOT NULL,
  `hatDressId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_shield_list`
--

CREATE TABLE `character_dressme_shield_list` (
  `charId` int NOT NULL,
  `shieldDressId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `character_dressme_weapon_list`
--

CREATE TABLE `character_dressme_weapon_list` (
  `charId` int NOT NULL,
  `weaponDressId` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
