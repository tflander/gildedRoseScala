package scalaRose

class GildedRose {
  def updateQuality(items: Seq[Item]) = {
    for (item <- items) yield {
      item.quality = item.qualityCalculator(item)

      if (item.daysLeftToSell > 0) {
        item.daysLeftToSell = item.daysLeftToSell - 1
      }
    }
  }
}

object GildedRose {

  def ageImprovedItemQualityCalculator(item: scalaRose.Item): Int = {
    if (item.quality < 50) {
      return item.quality + 1
    }

    return item.quality
  }

  def concertTicketItemQualityCalculator(item: scalaRose.Item): Int = {
    item.daysLeftToSell match {
      case daysLeft if (daysLeft == 0) => 0
      case daysLeft if (daysLeft <= 5) => item.quality + 3
      case daysLeft if (daysLeft <= 10) => item.quality + 2
      case _ => item.quality + 1
    }
  }

  def legendaryItemQualityCalculator(item: scalaRose.Item): Int = {
    return item.quality
  }

  def decreasingValueCalculator(decreaseFactor: Int)(item: scalaRose.Item): Int = {
    if (item.quality > 0) {
      val qualityDecrease = if (item.daysLeftToSell > 0) decreaseFactor else decreaseFactor * 2
      val candidateQuality = item.quality - qualityDecrease
      return if (item.quality >= qualityDecrease) candidateQuality else 0
    }

    return item.quality
  }
  
}