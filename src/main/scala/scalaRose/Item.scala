package scalaRose
import GildedRose._

case class Item(
  name: String,
  var daysLeftToSell: Int,
  var quality: Int,
  qualityCalculator: Item => Int = decreasingValueCalculator(1))

object Item {
  def AgeImprovedItem(name: String, daysLeftToSell: Int, quality: Int): Item =
    Item(name, daysLeftToSell, quality, ageImprovedItemQualityCalculator)

  def ConcertTicketItem(name: String, daysLeftToSell: Int, quality: Int): Item =
    Item(name, daysLeftToSell, quality, concertTicketItemQualityCalculator)

  def LegendaryItem(name: String, daysLeftToSell: Int, quality: Int): Item =
    Item(name, daysLeftToSell, quality, legendaryItemQualityCalculator)

  def ConjuredItem(name: String, daysLeftToSell: Int, quality: Int): Item =
    Item(name, daysLeftToSell, quality, decreasingValueCalculator(2))
}