import Foundation

struct AssetRecord: Codable, Identifiable {
    let id: UUID
    var name: String
    var amount: Double
    var type: String
    var category: String
}
