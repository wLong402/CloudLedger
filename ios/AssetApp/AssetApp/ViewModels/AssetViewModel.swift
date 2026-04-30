import Foundation

final class AssetViewModel: ObservableObject {
    @Published var records: [AssetRecord] = []
    @Published var name = ""
    @Published var amount = ""
    @Published var type = "asset"
    @Published var category = ""

    private let key = "asset_records_swift"

    init() {
        load()
    }

    func addRecord() {
        guard !name.trimmingCharacters(in: .whitespaces).isEmpty else { return }
        guard let value = Double(amount), value > 0 else { return }
        let record = AssetRecord(
            id: UUID(),
            name: name.trimmingCharacters(in: .whitespaces),
            amount: value,
            type: type,
            category: category.trimmingCharacters(in: .whitespaces)
        )
        records.insert(record, at: 0)
        name = ""
        amount = ""
        category = ""
        save()
    }

    func delete(at offsets: IndexSet) {
        records.remove(atOffsets: offsets)
        save()
    }

    var assetTotal: Double {
        records.filter { $0.type == "asset" }.map(\.amount).reduce(0, +)
    }

    var debtTotal: Double {
        records.filter { $0.type == "debt" }.map(\.amount).reduce(0, +)
    }

    var netWorth: Double {
        assetTotal - debtTotal
    }

    private func load() {
        guard let data = UserDefaults.standard.data(forKey: key) else { return }
        if let decoded = try? JSONDecoder().decode([AssetRecord].self, from: data) {
            records = decoded
        }
    }

    private func save() {
        if let data = try? JSONEncoder().encode(records) {
            UserDefaults.standard.set(data, forKey: key)
        }
    }
}
