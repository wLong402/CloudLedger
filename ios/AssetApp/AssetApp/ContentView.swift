import SwiftUI

struct ContentView: View {
    @StateObject private var vm = AssetViewModel()

    var body: some View {
        NavigationView {
            VStack(spacing: 10) {
                VStack(alignment: .leading, spacing: 6) {
                    Text("资产 ¥\(vm.assetTotal, specifier: "%.2f")")
                    Text("负债 ¥\(vm.debtTotal, specifier: "%.2f")")
                    Text("净资产 ¥\(vm.netWorth, specifier: "%.2f")")
                        .fontWeight(.bold)
                }
                .frame(maxWidth: .infinity, alignment: .leading)

                TextField("名称", text: $vm.name)
                    .textFieldStyle(.roundedBorder)
                TextField("金额", text: $vm.amount)
                    .keyboardType(.decimalPad)
                    .textFieldStyle(.roundedBorder)
                Picker("类型", selection: $vm.type) {
                    Text("资产").tag("asset")
                    Text("负债").tag("debt")
                }
                .pickerStyle(.segmented)
                TextField("分类", text: $vm.category)
                    .textFieldStyle(.roundedBorder)
                Button("添加") {
                    vm.addRecord()
                }
                .buttonStyle(.borderedProminent)
                .frame(maxWidth: .infinity, alignment: .trailing)

                List {
                    ForEach(vm.records) { item in
                        HStack {
                            VStack(alignment: .leading) {
                                Text(item.name).fontWeight(.bold)
                                Text("\(item.type) · \(item.category.isEmpty ? "未分类" : item.category)")
                                    .font(.caption)
                            }
                            Spacer()
                            Text("¥\(item.amount, specifier: "%.2f")")
                        }
                    }
                    .onDelete(perform: vm.delete)
                }
            }
            .padding()
            .navigationTitle("个人资产管理")
        }
    }
}
