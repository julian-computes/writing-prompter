import os
import markovify

if __name__ == '__main__':
    data_folder = 'src/main/resources/data'
    data_files = filter(lambda f: f.endswith('.txt'), os.listdir(data_folder))
    text = ''
    for f in data_files:
        with open(f'{data_folder}/{f}', encoding='utf8') as fp:
            text += fp.read() + ' '

    model = markovify.Text(text)
    for i in range(20):
        print(f'Prompt {i}:')
        print(model.make_sentence())
        print('')
