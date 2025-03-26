import base64
with open("dat", "rb") as f:
    exec(base64.b64decode(f.read()))
