### 説明
Kotlin と Ktor で作る TODO アプリケーション。
### 目的
Kotlin, TDD, DDD, クリーンアーキテクチャの練習。
### 技術
- Kotlin
- Ktor
- Exposed
- MySQL
- docker compose
- JUnit
- Gauge (別レポジトリ)

### 残りやること
- ドメイン（id, title, description）
- ユースケース
- ゲートウェイ
- ドライバー
- [マルチモジュール構成にする](https://qiita.com/kasa_le/items/db0d84e3e868ff14bc2b)
- README更新
- ドメイン（）

### ディレクトリ構成
```
src.main.kotlin.com/
├── todo/
│ │ └── plugins
│ │ └── Application.kt(main)
│ │ └── dto/ // データ転送オブジェクト
│ │ ├── entities/ // エンティティ（ドメインモデル）
│ │ ├── gateway/ // ユースケース、DBとの中間層(未実装)
│ │ ├── database/
│ │ ├── const/ // 定数
```

```
entities: ドメインモデル（エンティティ）を含む。ビジネスロジックがここに位置します。

repositories: ドメインリポジトリを含む。データ永続化のためのインターフェースを定義します。

services: ドメインサービスを含む。エンティティに紐付かないビジネスロジックを定義します。

modules: アプリケーション層のモジュールを含む。ユースケースや API コントローラーを配置します。

dto: データ転送オブジェクトを含む。API のリクエストやレスポンスのデータ構造を定義します。

infrastructure: インフラストラクチャ層を含む。データベースや外部 API とのインターフェースを実装します。

database: データベース関連の実装を含む。
```

### レイヤーの説明

```
Client → Controller → Service → Repository → DB

Client...リクエストを投げる主体。今回は `curl` コマンドで実行する。

Controller...リクエストを受け付ける層。入力バリデーションも行う。

Service...ビジネスロジックの定義をする層。

Repository...DBとのやり取りを行う層。実装ではORマッパーの `TypeORM` を使用している。

```

### ユーザーストーリー
```
TODOを作成することができる(POST:/todo)
TODOを全件取得することができる(GET:/todo)
TODOを1件取得することができる(GET:/todo/id)
TODOを更新することができる(PUT:/todo/id)
TODOを削除することができる(DELETE:/todo/id)
TODOの状態を見ることができる(GET:/todo/status)
TODOの優先度を見ることができる(GET:/todo/priority)
```
### サンプルリクエスト

##### TODO を全件取得する
```
curl "localhost:8080/todo"
```
##### TODO を 1 件取得する
```
curl "localhost:8080/todo/1"
```
##### TODO を 1 件作成する
```
curl -X POST 'http://localhost:8080/todo' \
-H 'Content-Type: application/json' \
-d '{
  "title": "Your First Todo Title",
  "description": "Your First Todo Description"
}'
```
##### TODO を 1 件更新する
```
curl -X PUT "localhost:8080/todo/1" \
-H "Content-Type: application/json" \
 -d '{
    "title": "update title",
    "description": "this is upated data"
}'
```
##### TODO を 1 件削除する
```
curl -X DELETE "localhost:8080/todo/3"
```