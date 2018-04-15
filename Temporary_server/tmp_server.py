from flask import Flask, jsonify

app = Flask(__name__)



list = [
	{
		'id': "1",
		'description': "kek",
	},
	{
		'id': "2",
		'description': "VK",
	},
	{
		'id': "3",
		'description': "Sucks",
	},
	{
		'id': "4",
		'description': "Kekhub.com",
	},
	{
		'id': "5",
		'description': "странная папка кэша 40Гб",
	},
]

@app.route('/accounts/api/v1.0/', methods=['GET'])
def get_tasks():
    return jsonify(list)



if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')